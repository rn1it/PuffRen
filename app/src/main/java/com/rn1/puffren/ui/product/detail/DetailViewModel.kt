package com.rn1.puffren.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.source.PuffRenRepository

class DetailViewModel(
    val repository: PuffRenRepository,
    val product: Product
) : ViewModel(){

    private val _leaveDetail = MutableLiveData<Boolean>()

    val leaveDetail: LiveData<Boolean>
        get() = _leaveDetail

    fun leaveDetail() {
        _leaveDetail.value = true
    }

}