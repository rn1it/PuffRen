package com.rn1.puffren.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.ItemPackage
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.source.PuffRenRepository

class DetailViewModel(
    val repository: PuffRenRepository,
    val product: Product
) : ViewModel(){

    private val _itemPackage = MutableLiveData<ItemPackage>()
    val itemPackage: LiveData<ItemPackage>
        get() = _itemPackage

    private val _show2Cart = MutableLiveData<Boolean>()
    val show2Cart: LiveData<Boolean>
        get() = _show2Cart

    private val _leaveDetail = MutableLiveData<Boolean>()
    val leaveDetail: LiveData<Boolean>
        get() = _leaveDetail

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