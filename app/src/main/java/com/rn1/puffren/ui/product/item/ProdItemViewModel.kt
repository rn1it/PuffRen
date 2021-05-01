package com.rn1.puffren.ui.product.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.ui.product.ProdTypeFilter
import com.rn1.puffren.util.Logger
import kotlinx.coroutines.launch

class ProdItemViewModel(
    private val repository: PuffRenRepository,
    prodTypeFilter: ProdTypeFilter
): ViewModel() {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    /**
     * if use paging
     */
    //private val sourceFactory = PagingDataSourceFactory(prodTypeFilter)
    //val pagingDataProducts: LiveData<PagedList<Product>> = sourceFactory.toLiveData(6, null)

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getProductListByType(prodTypeFilter)
    }

    private fun getProductListByType(prodTypeFilter: ProdTypeFilter){

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING
            val result = repository.getProductListByType(prodTypeFilter.value)

            _productList.value = when(result){
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is DataResult.Fail -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.error
                    null
                }
                is DataResult.Error -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.exception.toString()
                    null
                }
            }
        }
    }

    fun navigateToProductDetail(product: Product){
        _product.value = product
    }

    fun navigateToProductDetailDone(){
        _product.value = null
    }
}