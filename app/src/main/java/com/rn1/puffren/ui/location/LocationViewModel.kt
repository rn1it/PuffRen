package com.rn1.puffren.ui.location

import androidx.lifecycle.ViewModel
import com.rn1.puffren.util.Logger

class LocationViewModel: ViewModel() {

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

}