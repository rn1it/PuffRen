package com.rn1.puffren.ui.product

import androidx.lifecycle.ViewModel
import com.rn1.puffren.util.Logger

class ProdViewModel: ViewModel() {

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }
}