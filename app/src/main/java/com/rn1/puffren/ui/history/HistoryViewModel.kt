package com.rn1.puffren.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.Logger

class HistoryViewModel(
    val repository: PuffRenRepository
): ViewModel() {

    private val _navigateToReportItem = MutableLiveData<Boolean>()
    val navigateToReportItem: LiveData<Boolean>
        get() = _navigateToReportItem

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    fun navigateToReportItem(){
        _navigateToReportItem.value = true
    }

    fun navigateToReportItemDone(){
        _navigateToReportItem.value = null
    }

}