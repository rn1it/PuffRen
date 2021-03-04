package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.product.ProdTypeFilter
import com.rn1.puffren.ui.product.item.ProdItemViewModel

@Suppress("UNCHECKED_CAST")
class ProdItemViewModelFactory(
    private val puffRenRepository: PuffRenRepository,
    private val prodTypeFilter: ProdTypeFilter
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>)=
        with(modelClass) {
            when {
                isAssignableFrom(ProdItemViewModel::class.java) ->
                    ProdItemViewModel(puffRenRepository, prodTypeFilter)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}