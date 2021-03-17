package com.rn1.puffren.data.source.remote

import com.rn1.puffren.R
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Login
import com.rn1.puffren.data.LoginResult
import com.rn1.puffren.data.source.PuffRenDataSource
import com.rn1.puffren.network.PuffrenApi
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.Util.getString
import com.rn1.puffren.util.Util.isInternetConnected

object PuffRenRemoteDataSource: PuffRenDataSource {

    override suspend fun login(login: Login): DataResult<LoginResult> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
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
}