package com.rn1.puffren.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.ReportOpenStatus
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

    private val _reportOpenStatus =  MutableLiveData<ReportOpenStatus>()
    val reportOpenStatus: LiveData<ReportOpenStatus>
        get() = _reportOpenStatus

    private val _location = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _location

    private val _locationOptions = MutableLiveData<List<String>>()
    val locationOptions: LiveData<List<String>>
        get() = _locationOptions

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

        // TODO move to onResume()
        // getReportStatus()
        getPartnerLocations()
    }

    fun getReportStatus(){

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            _reportStatus.value = when(val result = repository.getReportStatus(UserManager.userToken!!)) {
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

    private fun getPartnerLocations(){

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            _locationOptions.value = when(val result = repository.getPartnerLocations(UserManager.userToken!!)) {
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

    fun reportForToday(location: String?, openStatus: Int, recordId: String?) {

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            val reportOpenStatus = ReportOpenStatus(openLocation = location, openStatus = openStatus, recordId = recordId)

             when(val result = repository.reportForToday(UserManager.userToken!!, reportOpenStatus)) {
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    _reportOpenStatus.value = result.data
                    UserManager.recordId = result.data.recordId
                    getReportStatus()
                }
                is DataResult.Fail -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.error
                    _reportOpenStatus.value = null
                }
                is DataResult.Error -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.exception.toString()
                    _reportOpenStatus.value = null
                }
            }
        }
    }

    fun selectLocationOption(location: String){
        _location.value = location
    }
}