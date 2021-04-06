package com.rn1.puffren.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.ItemPackage
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import kotlinx.coroutines.launch

class DetailViewModel(
    val repository: PuffRenRepository,
    val arguments: Product
) : ViewModel(){

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _itemPackage = MutableLiveData<ItemPackage>()
    val itemPackage: LiveData<ItemPackage>
        get() = _itemPackage

    private val _show2Cart = MutableLiveData<Boolean>()
    val show2Cart: LiveData<Boolean>
        get() = _show2Cart

    private val _leaveDetail = MutableLiveData<Boolean>()
    val leaveDetail: LiveData<Boolean>
        get() = _leaveDetail

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        getProductDetail(arguments.id)
    }

    private fun getProductDetail(id: String){

        viewModelScope.launch {

//            _status.value = LoadApiStatus.LOADING
            val result = repository.getProductDetail(id)

            _product.value = when (result) {
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is DataResult.Error -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value =  result.exception.toString()
                    null
                }
                is DataResult.Fail -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.error
                    null
                }
            }
        }
    }

    fun leaveDetail() {
        _leaveDetail.value = true
    }

    fun show2cart() {
        _show2Cart.value = true
    }

    fun setPackage(itemPackage: ItemPackage) {
        _itemPackage.value = itemPackage
    }

}