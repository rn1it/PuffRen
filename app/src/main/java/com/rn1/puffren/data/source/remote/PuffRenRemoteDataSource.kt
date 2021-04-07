package com.rn1.puffren.data.source.remote

import com.rn1.puffren.R
import com.rn1.puffren.data.*
import com.rn1.puffren.data.source.PuffRenDataSource
import com.rn1.puffren.network.PuffrenApi
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.Util.getString
import com.rn1.puffren.util.Util.isInternetConnected

object PuffRenRemoteDataSource: PuffRenDataSource {

    override suspend fun getHomePageItem(): DataResult<List<HomePageItem>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = PuffrenApi.retrofitService.getHomePageItem()
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun login(login: Login): DataResult<LoginResult> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.login(login)

            listResult.error?.let {
                return DataResult.Fail(it)
            }
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getProductListByType(type: String): DataResult<List<Product>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getProductListByType(type)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getProductDetail(id: String): DataResult<Product> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getProductDetail(id)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }
}