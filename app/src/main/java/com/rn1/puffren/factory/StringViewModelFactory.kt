package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.report.sale.SaleReportViewModel

@Suppress("UNCHECKED_CAST")
class StringViewModelFactory(
    private val puffRenRepository: PuffRenRepository,
    private val string: String
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>)=
        with(modelClass) {
            when {
                isAssignableFrom(SaleReportViewModel::class.java) ->
                    SaleReportViewModel(puffRenRepository, string)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}