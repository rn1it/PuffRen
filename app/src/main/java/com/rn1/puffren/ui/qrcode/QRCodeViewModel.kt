package com.rn1.puffren.ui.qrcode

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.Coupon
import com.rn1.puffren.data.CouponType
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.User
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class QRCodeViewModel(
    val repository: PuffRenRepository,
    private val arguments: User?
): ViewModel() {

    private val _user = MutableLiveData<User>().apply {
        arguments?.let {
            value = it
        }
    }

    val user: LiveData<User>
        get() = _user

    private val _coupons = MutableLiveData<List<Coupon>>()
    val coupons: LiveData<List<Coupon>>
        get() = _coupons

    val countDownTimer = object : CountDownTimer(QR_CODE_EFFECTIVE_TIME, COUNT_DOWN_TIME) {
        override fun onTick(millisUntilFinished: Long) {
            val numberFormat = DecimalFormat("00")
            val min = millisUntilFinished / 60000 % 60
            val sec = millisUntilFinished / 1000 % 60
            _time.value = "${numberFormat.format(min)}:${numberFormat.format(sec)}"
        }

        override fun onFinish() {
            _time.value = null
            _timeIsUp.value = true
        }
    }

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    private val _timeIsUp = MutableLiveData<Boolean>()
    val timeIsUp: LiveData<Boolean>
        get() = _timeIsUp

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getCoupon()
    }

    private fun getCoupon(){

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            _coupons.value = when(val result = repository.getCoupon(
                UserManager.userToken!!,
                CouponType.ALL.value
            )) {
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is DataResult.Fail -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.error
                    null
                }
                is DataResult.Error -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.exception.toString()
                    null
                }
            }
        }
    }
    fun resetTimeIsUp() {
        _timeIsUp.value = null
    }

    companion object {
        const val QR_CODE_EFFECTIVE_TIME = 180000L
        const val COUNT_DOWN_TIME = 1000L
    }
}