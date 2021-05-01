package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.data.ItemPackage
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.cart.CartViewModel
import com.rn1.puffren.ui.product.ProdTypeFilter
import com.rn1.puffren.ui.product.item.ProdItemViewModel

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory(
    private val puffRenRepository: PuffRenRepository,
    private val product: Product?,
    private val itemPackage: ItemPackage?
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>)=
        with(modelClass) {
            when {
                isAssignableFrom(CartViewModel::class.java) ->
                    CartViewModel(puffRenRepository, product, itemPackage)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}