package com.rn1.puffren.ui.cart

import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.ItemPackage
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.Logger

class CartViewModel(
    val repository: PuffRenRepository,
    val argProduct: Product?,
    val argItemPackage: ItemPackage?
): ViewModel() {

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    fun increaseAmount(){

    }

    fun decreaseAmount(){

    }
}