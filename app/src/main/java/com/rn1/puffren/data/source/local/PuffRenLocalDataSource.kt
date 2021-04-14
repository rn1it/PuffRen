package com.rn1.puffren.data.source.local

import com.rn1.puffren.data.*
import com.rn1.puffren.data.source.PuffRenDataSource

class PuffRenLocalDataSource: PuffRenDataSource {
    override suspend fun getHomePageItem(): DataResult<List<HomePageItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun login(login: Login): DataResult<LoginResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getLoginUser(token: String): DataResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductListByType(type: String): DataResult<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetail(id: String): DataResult<Product> {
        TODO("Not yet implemented")
    }
}