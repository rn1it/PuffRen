package com.rn1.puffren.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.Logger

class HistoryViewModel(
    val repository: PuffRenRepository
): ViewModel() {

    private val _navigateToReportItem = MutableLiveData<Boolean>()
    val navigateToReportItem: LiveData<Boolean>
        get() = _navigateToReportItem

    private val _navigateToAdvanceReport = MutableLiveData<Boolean>()
    val navigateToAdvanceReport: LiveData<Boolean>
        get() = _navigateToAdvanceReport

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