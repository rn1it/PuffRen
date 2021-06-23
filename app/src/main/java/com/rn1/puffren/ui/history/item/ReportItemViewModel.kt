package com.rn1.puffren.ui.history.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.ReportItem
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch

class ReportItemViewModel(
    val repository: PuffRenRepository
): ViewModel() {

    private val _reportItems = MutableLiveData<List<ReportItem>>()
    val reportItems: LiveData<List<ReportItem>>
        get() = _reportItems

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

        getReportItem()
    }

    private fun getReportItem(){

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            _reportItems.value = when (val result = repository.getReportItems(UserManager.userToken!!)) {
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is DataResult.Error -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value =  result.exception.toString()
                    null
                }
                is DataResult.Fail -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.error
                    null
                }
            }
        }
    }
}