package org.piramalswasthya.sakhi.ui.home_activity.non_communicable_disease.tb_screening.form

import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.piramalswasthya.sakhi.configuration.TBScreeningDataset
import org.piramalswasthya.sakhi.database.shared_preferences.PreferenceDao
import org.piramalswasthya.sakhi.model.TBScreeningCache
import org.piramalswasthya.sakhi.repositories.BenRepo
import org.piramalswasthya.sakhi.repositories.TBRepo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TBScreeningFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    preferenceDao: PreferenceDao,
    @ApplicationContext context: Context,
    private val tbRepo: TBRepo,
    private val benRepo: BenRepo
) : ViewModel() {
    val benId =
        TBScreeningFormFragmentArgs.fromSavedStateHandle(savedStateHandle).benId

    enum class State {
        IDLE, SAVING, SAVE_SUCCESS, SAVE_FAILED
    }

    private val _state = MutableLiveData(State.IDLE)
    val state: LiveData<State>
        get() = _state

    private val _benName = MutableLiveData<String>()
    val benName: LiveData<String>
        get() = _benName
    private val _benAgeGender = MutableLiveData<String>()
    val benAgeGender: LiveData<String>
        get() = _benAgeGender

    private val _recordExists = MutableLiveData<Boolean>()
    val recordExists: LiveData<Boolean>
        get() = _recordExists

    //    private lateinit var user: UserDomain
    private val dataset =
        TBScreeningDataset(context, preferenceDao.getCurrentLanguage())
    val formList = dataset.listFlow

    var suspectedTB: String? = null

    var suspectedTBFamily: String? = null

    private lateinit var tbScreeningCache: TBScreeningCache

    init {
        viewModelScope.launch {
            val ben = benRepo.getBenFromId(benId)?.also { ben ->
                _benName.value =
                    "${ben.firstName} ${if (ben.lastName == null) "" else ben.lastName}"
                _benAgeGender.value = "${ben.age} ${ben.ageUnit?.name} | ${ben.gender?.name}"
                tbScreeningCache = TBScreeningCache(
                    benId = ben.beneficiaryId,
                )
            }

            tbRepo.getTbsn(benId)?.let {
                tbScreeningCache = it
                _recordExists.value = true
            } ?: run {
                _recordExists.value = false
            }

            dataset.setUpPage(
                ben,
                if (recordExists.value == true) tbScreeningCache else null
            )

        }
    }

    fun updateListOnValueChanged(formId: Int, index: Int) {
        viewModelScope.launch {
            dataset.updateList(formId, index)
        }

    }


    fun getAlerts() {
        suspectedTB = dataset.isTbSuspected()
        suspectedTBFamily = dataset.isTbSuspectedFamily()
    }
    fun saveForm() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _state.postValue(State.SAVING)
                    dataset.mapValues(tbScreeningCache, 1)
                    tbRepo.saveTbsn(tbScreeningCache)
                    _state.postValue(State.SAVE_SUCCESS)
                } catch (e: Exception) {
                    Timber.d("saving PWR data failed!!")
                    _state.postValue(State.SAVE_FAILED)
                }
            }
        }
    }

    fun resetState() {
        _state.value = State.IDLE
    }

}
