package com.rn1.puffren.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Day
import com.rn1.puffren.data.ReportStatus
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch

class ReportViewModel(
    val repository: PuffRenRepository
) : ViewModel(){

    private val _reportStatus =  MutableLiveData<ReportStatus>()
    val reportStatus: LiveData<ReportStatus>
        get() = _reportStatus

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

        getReportStatus()
    }

    private fun getReportStatus(){

        viewModelScope.launch {

            _reportStatus.value = when(val result = repository.getReportStatus(UserManager.userToken!!)) {
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is DataResult.Fail -> {
                    _error.value = result.error
                    null
                }
                is DataResult.Error -> {
                    _error.value = result.exception.toString()
                    null
                }
            }
        }
    }
}