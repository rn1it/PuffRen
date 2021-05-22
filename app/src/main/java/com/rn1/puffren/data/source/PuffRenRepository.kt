package com.rn1.puffren.data.source

import com.rn1.puffren.data.*

interface PuffRenRepository {

    suspend fun getHomePageItem(): DataResult<List<HomePageItem>>

    suspend fun login(login: Login): DataResult<LoginResult>

    suspend fun getLoginUser(token: String): DataResult<User>

    suspend fun registry(user:User): DataResult<String>

    suspend fun getProductListByType(type: String): DataResult<List<Product>>

    suspend fun getProductDetail(id: String): DataResult<Product>

    suspend fun getReportItems(token: String): DataResult<List<ReportItem>>
}