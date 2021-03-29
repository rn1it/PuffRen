package com.rn1.puffren.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.source.PuffRenRepository

class ProfileViewModel(repository: PuffRenRepository) : ViewModel() {

    private val _navigateToSaleReport = MutableLiveData<Boolean>()
    val navigateToSaleReport: LiveData<Boolean>
        get() = _navigateToSaleReport

    private val _navigateToSaleReportHistory = MutableLiveData<Boolean>()
    val navigateToSaleReportHistory: LiveData<Boolean>
        get() = _navigateToSaleReportHistory

    private val _navigateToQRCode = MutableLiveData<Boolean>()
    val navigateToQRCode: LiveData<Boolean>
        get() = _navigateToQRCode



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
}