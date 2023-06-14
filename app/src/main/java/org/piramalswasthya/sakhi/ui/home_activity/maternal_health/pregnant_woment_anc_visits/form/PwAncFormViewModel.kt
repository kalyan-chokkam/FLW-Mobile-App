package org.piramalswasthya.sakhi.ui.home_activity.maternal_health.pregnant_woment_anc_visits.form

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.piramalswasthya.sakhi.configuration.PregnantWomanAncVisitDataset
import org.piramalswasthya.sakhi.database.shared_preferences.PreferenceDao
import org.piramalswasthya.sakhi.model.PregnantWomanAncCache
import org.piramalswasthya.sakhi.repositories.BenRepo
import org.piramalswasthya.sakhi.repositories.MaternalHealthRepo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PwAncFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    preferenceDao: PreferenceDao,
    @ApplicationContext context: Context,
    private val maternalHealthRepo: MaternalHealthRepo,
    private val benRepo: BenRepo
) : ViewModel() {

    enum class State {
        IDLE, SAVING, SAVE_SUCCESS, SAVE_FAILED
    }

    private val benId =
        PwAncFormFragmentArgs.fromSavedStateHandle(savedStateHandle).benId
    private val visitNumber =
        PwAncFormFragmentArgs.fromSavedStateHandle(savedStateHandle).visitNumber


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
        PregnantWomanAncVisitDataset(visitNumber, context, preferenceDao.getCurrentLanguage())
    val formList = dataset.listFlow

    private lateinit var ancCache: PregnantWomanAncCache

    init {
        viewModelScope.launch {
            val ben = maternalHealthRepo.getBenFromId(benId)?.also { ben ->
                _benName.value =
                    "${ben.firstName} ${if (ben.lastName == null) "" else ben.lastName}"
                _benAgeGender.value = "${ben.age} ${ben.ageUnit?.name} | ${ben.gender?.name}"
                ancCache = PregnantWomanAncCache(
                    benId = ben.beneficiaryId,
                    visitNumber = visitNumber
                )
            }
            val registerRecord = maternalHealthRepo.getSavedRegistrationRecord(benId)!!
            maternalHealthRepo.getSavedAncRecord(benId, visitNumber)?.let {
                ancCache = it
                _recordExists.value = true
            } ?: run {
                _recordExists.value = false
            }

            dataset.setUpPage(
                ben,
                registerRecord,
                if (recordExists.value == true) ancCache else null
            )


        }
    }

    fun updateListOnValueChanged(formId: Int, index: Int) {
        viewModelScope.launch {
            dataset.updateList(formId, index)
        }

    }


    fun saveForm() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _state.postValue(State.SAVING)
                    dataset.mapValues(ancCache, 1)
                    maternalHealthRepo.persistAncRecord(ancCache)
                    if(ancCache.pregnantWomanDelivered==true){
                        maternalHealthRepo.getSavedRegistrationRecord(benId)?.let {
                            it.active = false
                            maternalHealthRepo.persistRegisterRecord(it)
                        }
                        maternalHealthRepo.getBenFromId(benId)?.let {
                            dataset.updateBenRecordForDelivered(it)
                            benRepo.persistRecord(it)
                        }
                    }
                    _state.postValue(State.SAVE_SUCCESS)
                } catch (e: Exception) {
                    Timber.d("saving PW-ANC data failed!!")
                    _state.postValue(State.SAVE_FAILED)
                }
            }
        }
    }

    fun setRecordExist(b: Boolean) {
        _recordExists.value = b

    }

    fun getIndexOfWeeksOfPregnancy(): Int = dataset.getWeeksOfPregnancy()


}