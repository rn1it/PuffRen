package com.rn1.puffren.ui.history.advance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.ReportOpenStatus
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import com.rn1.puffren.util.Util.getTimeFormat
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdvanceReportViewModel(
    val repository: PuffRenRepository
) : ViewModel(){

    private val timeFormat = getTimeFormat()

    private val _reportResult = MutableLiveData<String>()
    val reportResult: LiveData<String>
        get() = _reportResult

    val reportDate = MutableLiveData<String>().apply {
        value = "2021-05-30"
    }

    val openTime = MutableLiveData<String>().apply {
        value = timeFormat.format(Calendar.getInstance(Locale.TAIWAN).time)
    }

    private val _location = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _location

    private val _locationOptions = MutableLiveData<List<String>>()
    val locationOptions: LiveData<List<String>>
        get() = _locationOptions

    val openLocation = MutableLiveData<String>()

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

        getPartnerLocations()
    }

    private fun getPartnerLocations(){
        viewModelScope.launch {

            _locationOptions.value = when(val result = repository.getPartnerLocations(UserManager.userToken!!)) {
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

    fun reportInAdvance(){
        viewModelScope.launch {

            val reportOpenStatus = ReportOpenStatus(
                    reportDate = reportDate.value,
                    openTime = openTime.value,
                    openLocation = openLocation.value
            )

            _reportResult.value = when(val result = repository.reportInAdvance(UserManager.userToken!!, reportOpenStatus)) {
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    result.data.message
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

    fun selectLocationOption(location: String){
        _location.value = location
    }
}