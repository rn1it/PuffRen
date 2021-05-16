package com.rn1.puffren.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.User
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: PuffRenRepository,
    private val arguments: User?
    ) : ViewModel() {

    private val _user = MutableLiveData<User>().apply {
        arguments?.let {
            value = it
        }
    }

    val user: LiveData<User>
        get() = _user

    private val _navigateToSaleReport = MutableLiveData<Boolean>()
    val navigateToSaleReport: LiveData<Boolean>
        get() = _navigateToSaleReport

    private val _navigateToSaleReportHistory = MutableLiveData<Boolean>()
    val navigateToSaleReportHistory: LiveData<Boolean>
        get() = _navigateToSaleReportHistory

    private val _navigateToQRCode = MutableLiveData<Boolean>()
    val navigateToQRCode: LiveData<Boolean>
        get() = _navigateToQRCode

    private val _navigateToOrder = MutableLiveData<Boolean>()
    val navigateToOrder: LiveData<Boolean>
        get() = _navigateToOrder

    private val _navigateToEditMember = MutableLiveData<Boolean>()
    val navigateToEditMember: LiveData<Boolean>
        get() = _navigateToEditMember

    private val _navigateToActivity = MutableLiveData<Boolean>()
    val navigateToActivity: LiveData<Boolean>
        get() = _navigateToActivity

    private val _navigateToMemberQRCode = MutableLiveData<Boolean>()
    val navigateToMemberQRCode: LiveData<Boolean>
        get() = _navigateToMemberQRCode

    private val _navigateToMemberCoupon = MutableLiveData<Boolean>()
    val navigateToMemberCoupon: LiveData<Boolean>
        get() = _navigateToMemberCoupon

    private val _navigateToPerformance = MutableLiveData<Boolean>()
    val navigateToPerformance: LiveData<Boolean>
        get() = _navigateToPerformance

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

        if (user.value == null) {
            UserManager.userToken?.let {
                getUserProfile(it)
            }
        }
    }

    /**
     * for test
     */
    val testIsVendor = MutableLiveData<Boolean>().apply {
        value = true
    }


    // TODO
    fun switchUserType(){
        testIsVendor.value = when (testIsVendor.value) {
            true -> false
            else -> true
        }
    }


    private fun getUserProfile(token: String){
        viewModelScope.launch {

            when(val result = repository.getLoginUser(token)){

                is DataResult.Success -> {
                    val user = result.data
                    Logger.d("登入使用者: $user")
                    _user.value = user
                }

                is DataResult.Fail -> {
                    Logger.d("Fail")
                }

                is DataResult.Error -> {
                    Logger.d("Error")
                }
            }
        }
    }

    fun navigateToSaleReport(){
        _navigateToSaleReport.value = true
    }

    fun navigateToSaleReportDone(){
        _navigateToSaleReport.value = null
    }

    fun navigateToSaleReportHistory(){
        _navigateToSaleReportHistory.value = true
    }

    fun navigateToSaleReportHistoryDone(){
        _navigateToSaleReportHistory.value = null
    }

    fun navigateToQRCode(){

        _navigateToQRCode.value = true
    }

    fun navigateToQRCodeDone(){
        _navigateToQRCode.value = null
    }

    fun navigateToOrder(){
        _navigateToOrder.value = true
    }

    fun navigateToOrderDone(){
        _navigateToOrder.value = null
    }

    fun navigateToEditMember(){
        _navigateToEditMember.value = true
    }

    fun navigateToEditMemberDone(){
        _navigateToEditMember.value = null
    }

    fun navigateToActivity(){
        _navigateToActivity.value = true
    }

    fun navigateToActivityDone(){
        _navigateToActivity.value = null
    }

    fun navigateToMemberQRCode(){
        _navigateToMemberQRCode.value = true
    }

    fun navigateToMemberQRCodeDone(){
        _navigateToMemberQRCode.value = null
    }

    fun navigateToMemberCoupon(){
        _navigateToMemberCoupon.value = true
    }

    fun navigateToMemberCouponDone(){
        _navigateToMemberCoupon.value = null
    }

    fun navigateToPerformance(){
        _navigateToPerformance.value = true
    }

    fun navigateToPerformanceDone(){
        _navigateToPerformance.value = null
    }
}