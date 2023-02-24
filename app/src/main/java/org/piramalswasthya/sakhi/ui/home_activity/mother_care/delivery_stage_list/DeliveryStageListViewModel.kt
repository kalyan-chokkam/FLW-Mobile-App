package org.piramalswasthya.sakhi.ui.home_activity.mother_care.delivery_stage_list

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.piramalswasthya.sakhi.model.BenBasicDomain
import org.piramalswasthya.sakhi.model.UserDomain
import org.piramalswasthya.sakhi.repositories.BenRepo
import org.piramalswasthya.sakhi.repositories.UserRepo
import javax.inject.Inject

@HiltViewModel
class DeliveryStageListViewModel @Inject constructor(
    private val benRepo: BenRepo,
    private val userRepo: UserRepo,
) : ViewModel() {

    val deliveryList = benRepo.deliveryList
    private lateinit var user: UserDomain
    private val _benList = MutableLiveData<List<BenBasicDomain>>()
    val benList: LiveData<List<BenBasicDomain>>
        get() = _benList

    init {
        viewModelScope.launch {
            deliveryList.asFlow().collect {
                _benList.value = it
            }
            withContext(Dispatchers.IO) {
                user = userRepo.getLoggedInUser()!!
            }
        }
    }

    fun getUserId(): Int = user.userId

    fun filterText(filterText: String) {
        if (filterText == "")
            _benList.value = deliveryList.value
        else
            _benList.value = deliveryList.value?.filter {
                it.hhId.toString().contains(filterText) ||
                        it.benId.toString().contains(filterText) ||
                        it.regDate.contains((filterText)) ||
                        it.age.contains(filterText) ||
                        it.benName.lowercase().contains(filterText) ||
                        it.familyHeadName.contains(filterText) ||
                        it.benSurname?.contains(filterText) ?: false ||
                        it.typeOfList.contains(filterText) ||
                        it.mobileNo.contains(filterText) ||
                        it.gender.contains(filterText)

            }
    }


    fun manualSync(/*hhId: Long, benId: Long, locationRecord: LocationRecord*/) {
        viewModelScope.launch {
            benRepo.processNewBen()
        }
    }
}