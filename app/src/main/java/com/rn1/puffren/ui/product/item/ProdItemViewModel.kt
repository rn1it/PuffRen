package com.rn1.puffren.ui.product.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.product.ProdTypeFilter

class ProdItemViewModel(
    repository: PuffRenRepository,
    prodTypeFilter: ProdTypeFilter
): ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

//    private val sourceFactory = PagingDataSourceFactory(prodTypeFilter)

//    val pagingDataProducts: LiveData<PagedList<Product>> = sourceFactory.toLiveData(6, null)



    fun navigateToProductDetail(product: Product){
        _product.value = product
    }

    fun navigateToProductDetailDone(){
        _product.value = null
    }
}