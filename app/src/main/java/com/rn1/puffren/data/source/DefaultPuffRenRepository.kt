package com.rn1.puffren.data.source

import com.rn1.puffren.data.*
import kotlin.math.log

class DefaultPuffRenRepository(
    private val puffRenRemoteDataSource: PuffRenDataSource,
    private val puffRenLocalDataSource: PuffRenDataSource
): PuffRenRepository{

    override suspend fun getHomePageItem(): DataResult<List<HomePageItem>> {
        return puffRenRemoteDataSource.getHomePageItem()
    }

    override suspend fun login(login: Login): DataResult<LoginResult> {
        return puffRenRemoteDataSource.login(login)
    }

    override suspend fun getLoginUser(token: String): DataResult<User> {
        return puffRenRemoteDataSource.getLoginUser(token)
    }

    override suspend fun getProductListByType(type: String): DataResult<List<Product>> {
        return puffRenRemoteDataSource.getProductListByType(type)
    }

    override suspend fun getProductDetail(id: String): DataResult<Product> {
        return puffRenRemoteDataSource.getProductDetail(id)
    }
}