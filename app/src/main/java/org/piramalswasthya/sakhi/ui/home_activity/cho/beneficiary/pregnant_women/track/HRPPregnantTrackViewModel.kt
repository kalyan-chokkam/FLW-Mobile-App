package org.piramalswasthya.sakhi.ui.home_activity.cho.beneficiary.pregnant_women.track

import android.content.Context
import android.content.res.Configuration
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
import org.piramalswasthya.sakhi.configuration.HRPPregnantTrackDataset
import org.piramalswasthya.sakhi.database.shared_preferences.PreferenceDao
import org.piramalswasthya.sakhi.model.BenWithHRPTrackingCache
import org.piramalswasthya.sakhi.model.HRPPregnantTrackCache
import org.piramalswasthya.sakhi.repositories.BenRepo
import org.piramalswasthya.sakhi.repositories.HRPRepo
import timber.log.Timber
import java.util.Locale
import kotlin.math.max

@HiltViewModel
class HRPPregnantTrackViewModel @javax.inject.Inject
constructor(
    savedStateHandle: SavedStateHandle,
    preferenceDao: PreferenceDao,
    @ApplicationContext context: Context,
    private val hrpReo: HRPRepo,
    private val benRepo: BenRepo
) : ViewModel() {
    val benId =
        HRPPregnantTrackFragmentArgs.fromSavedStateHandle(savedStateHandle).benId

    val trackId =
        HRPPregnantTrackFragmentArgs.fromSavedStateHandle(savedStateHandle).trackId

    enum class State {
        IDLE, SAVING, SAVE_SUCCESS, SAVE_FAILED
    }

    private val _state = MutableLiveData(State.IDLE)
    val state: LiveData<State>
        get() = _state

    private val _benName = MutableLiveData<String>()
    val benName: LiveData<String>
        get() = _benName

    private val _benWithHrpt = MutableLiveData<BenWithHRPTrackingCache>()
    val benWithHrpt: LiveData<BenWithHRPTrackingCache>
        get() = _benWithHrpt

    private val _benAgeGender = MutableLiveData<String>()
    val benAgeGender: LiveData<String>
        get() = _benAgeGender

    private val _recordExists = MutableLiveData<Boolean>()
    val recordExists: LiveData<Boolean>
        get() = _recordExists

    private val _trackingDone = MutableLiveData<Boolean>()
    val trackingDone: LiveData<Boolean>
        get() = _trackingDone

    //    private lateinit var user: UserDomain
    private val dataset =
        HRPPregnantTrackDataset(context, preferenceDao.getCurrentLanguage())
    val formList = dataset.listFlow

    var isHighRisk: Boolean = false


    private lateinit var hrpPregnantTrackCache: HRPPregnantTrackCache

    init {
        loadData()
    }

    private val resources by lazy {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(preferenceDao.getCurrentLanguage().symbol))
        context.createConfigurationContext(configuration).resources
    }

    fun loadData() {
        viewModelScope.launch {
            _benWithHrpt.value = benRepo.getBenWithHRPT(benId)
            val ben = benRepo.getBenFromId(benId)?.also { ben ->
                _benName.value =
                    "${ben.firstName} ${if (ben.lastName == null) "" else ben.lastName}"
                _benAgeGender.value = "${ben.age} ${ben.ageUnit?.name} | ${ben.gender?.name}"
                hrpPregnantTrackCache = HRPPregnantTrackCache(
                    benId = ben.beneficiaryId,
                )
            }

            hrpReo.getHrPregTrackList(benId)?.let {
                when (it.size) {
                    0 -> hrpPregnantTrackCache.visit = "1st ANC"
                    1 -> hrpPregnantTrackCache.visit = "1st PMSMA"
                    2 -> hrpPregnantTrackCache.visit = "2nd PMSMA"
                    3 -> hrpPregnantTrackCache.visit = "3rd PMSMA"
                    else -> _trackingDone.value = trackId == 0
                }
            }

            hrpReo.getHRPTrack(trackId = trackId.toLong())?.let {
                if (trackId > 0) {
                    hrpPregnantTrackCache = it
                    _recordExists.value = true
                }

            } ?: run {
                _recordExists.value = false
            }

            val maxDovHrp = hrpReo.getMaxDoVHrp(benId)

            val maxDovNonHrp = hrpReo.getMaxDoVNonHrp(benId)
            var maxDov: Long =
                when {
                    maxDovHrp != null && maxDovNonHrp != null -> max(maxDovHrp, maxDovNonHrp)
                    maxDovHrp != null && maxDovNonHrp == null -> maxDovHrp
                    maxDovHrp == null && maxDovNonHrp != null -> maxDovNonHrp
                    else -> 0L
                }

            hrpReo.getPregnantAssess(benId)?.let {
                maxDov = maxOf(maxDov, it.lmpDate)
            }
            dataset.setUpPage(
                ben,
                hrpPregnantTrackCache.visit,
                if (recordExists.value == true) hrpPregnantTrackCache else null,
                maxDov
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

                    dataset.mapValues(hrpPregnantTrackCache, 1)
                    hrpReo.saveRecord(hrpPregnantTrackCache)

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

    fun getIndexOfRdPmsa() = dataset.getIndexOfRdPmsa()
    fun getIndexOfRdDengue() = dataset.getIndexOfRdDengue()
    fun getIndexOfRdFilaria() = dataset.getIndexOfRdFilaria()
    fun getIndexOfSevereAnemia() = dataset.getIndexOfSevereAnemia()
    fun getIndexOfPregInduced() = dataset.getIndexOfPregInduced()
    fun getIndexOfGest() = dataset.getIndexOfGest()
    fun getIndexOfHypothyroidism() = dataset.getIndexOfHypothyroidism()
    fun getIndexOfPolyhydromnios() = dataset.getIndexOfPolyhydromnios()
    fun getIndexOfOligohydromnios() = dataset.getIndexOfOligohydromnios()
    fun getIndexOfAntepartum() = dataset.getIndexOfAntepartum()
    fun getIndexOfMalPre() = dataset.getIndexOfMalPre()
    fun getIndexOfHiv() = dataset.getIndexOfHiv()
    fun getIndexOfRbg() = dataset.getIndexOfRbg()
    fun getIndexOfFbg() = dataset.getIndexOfFbg()
    fun getIndexOfPpbg() = dataset.getIndexOfPpbg()
    fun getIndexOfOgttLabel() = dataset.getIndexOfOgttLabel()
    fun getIndexOfFasting() = dataset.getIndexOfFasting()
    fun getIndexOfAfter() = dataset.getIndexOfafter()
    fun getIndexOfIfaQuantity() = dataset.getIndexOfIfaQuantity()
}