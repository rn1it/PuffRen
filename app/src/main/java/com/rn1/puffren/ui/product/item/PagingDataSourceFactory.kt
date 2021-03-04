package com.rn1.puffren.ui.product.item

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rn1.puffren.data.Product
import com.rn1.puffren.ui.product.ProdTypeFilter

class PagingDataSourceFactory(val type: ProdTypeFilter): DataSource.Factory<String, Product>() {

    private val sourceLiveData = MutableLiveData<PagingDataSource>()

    override fun create(): DataSource<String, Product> {
        val source = PagingDataSource(type)
        sourceLiveData.postValue(source)
        return source
    }

}