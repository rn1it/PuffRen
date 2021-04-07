package com.rn1.puffren.data.source

import com.rn1.puffren.data.*

interface PuffRenDataSource {

    suspend fun getHomePageItem(): DataResult<List<HomePageItem>>

    suspend fun login(login: Login): DataResult<LoginResult>

    suspend fun getProductListByType(type: String): DataResult<List<Product>>

    suspend fun getProductDetail(id: String): DataResult<Product>
}