package com.rn1.puffren.ui.history

import androidx.lifecycle.*
import androidx.navigation.NavDestination
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.data.*
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch

class HistoryViewModel(
    val repository: PuffRenRepository
): ViewModel() {

    private val _navigateToReportItem = MutableLiveData<Boolean>()
    val navigateToReportItem: LiveData<Boolean>
        get() = _navigateToReportItem

    private val _navigateToAdvanceReport = MutableLiveData<Boolean>()
    val navigateToAdvanceReport: LiveData<Boolean>
        get() = _navigateToAdvanceReport

    private val _saleCalendar = MutableLiveData<SaleCalendar>()
    val saleCalendar: LiveData<SaleCalendar>
        get() = _saleCalendar

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
    }

    fun navigate(destination: Int){
        when(destination){
            1 -> navigateToReportItem()
            2 -> navigateToAdvanceReport()
        }
    }

    fun getSaleCalendar(){

        viewModelScope.launch {

            _saleCalendar.value = when(val result = repository.getSaleCalendar(UserManager.userToken!!)) {
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

    private fun navigateToReportItem(){
        _navigateToReportItem.value = true
    }

    fun navigateToReportItemDone(){
        _navigateToReportItem.value = null
    }

    private fun navigateToAdvanceReport(){
        _navigateToAdvanceReport.value = true
    }

    fun navigateToAdvanceReportDone(){
        _navigateToAdvanceReport.value = null
    }
}