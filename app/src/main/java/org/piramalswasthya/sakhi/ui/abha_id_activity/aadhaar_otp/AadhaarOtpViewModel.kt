package org.piramalswasthya.sakhi.ui.abha_id_activity.aadhaar_otp

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.piramalswasthya.sakhi.repositories.AbhaIdRepo
import org.piramalswasthya.sakhi.ui.abha_id_activity.aadhaar_id.AadhaarIdViewModel
import javax.inject.Inject

@HiltViewModel
class AadhaarOtpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val abhaIdRepo: AbhaIdRepo
) : ViewModel() {

    enum class State {

        IDLE,
        LOADING,
        ERROR_SERVER,
        ERROR_NETWORK,
        SUCCESS
    }

    private val txnIdFromArgs = AadhaarOtpFragmentArgs.fromSavedStateHandle(savedStateHandle).txnId
    private val _state = MutableLiveData(State.IDLE)
    val state: LiveData<State>
        get() = _state

    private var _txnId: String? = null
    val txnId: String
        get() = _txnId!!

    fun verifyOtpClicked(otp: String) {
        _state.value = State.LOADING
        verifyAadharOtp(otp)


    }

    fun resetState() {
        _state.value = State.IDLE
    }

    private fun verifyAadharOtp(otp: String) {
        viewModelScope.launch {
            //_txnId = abhaIdRepo.verifyOtpForAadhar(otp, txnIdFromArgs)
            _txnId = abhaIdRepo.verifyOtpForAadharDummy(otp, txnIdFromArgs)
            _txnId?.also {
                _state.value = State.SUCCESS
            } ?: run {
                _state.value = State.ERROR_NETWORK
            }
        }
    }

}