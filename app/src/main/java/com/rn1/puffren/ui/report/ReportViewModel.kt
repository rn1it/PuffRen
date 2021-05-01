package com.rn1.puffren.ui.report

import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.Logger

class ReportViewModel(
    val repository: PuffRenRepository
) : ViewModel(){

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

}