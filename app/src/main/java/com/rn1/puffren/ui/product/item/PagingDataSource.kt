package com.rn1.puffren.ui.product.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.rn1.puffren.data.Product
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.ui.product.ProdTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PagingDataSource(val type: ProdTypeFilter): PageKeyedDataSource<String, Product>() {

    private val _statusInitialLoad = MutableLiveData<LoadApiStatus>()
    val statusInitialLoad: LiveData<LoadApiStatus>
        get() = _statusInitialLoad

    private val _errorInitialLoad = MutableLiveData<String>()
    val errorInitialLoad: LiveData<String>
        get() = _errorInitialLoad

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Product>
    ) {
        coroutineScope.launch {

            _statusInitialLoad.value = LoadApiStatus.LOADING

//            val result =


        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Product>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Product>) {
        TODO("Not yet implemented")
    }




}