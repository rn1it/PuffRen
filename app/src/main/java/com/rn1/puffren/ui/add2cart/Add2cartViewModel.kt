package com.rn1.puffren.ui.add2cart

import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.Logger

class Add2cartViewModel(val repository: PuffRenRepository): ViewModel() {
    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }
}