package com.rn1.puffren.data.source

import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Login
import com.rn1.puffren.data.LoginResult
import kotlin.math.log

class DefaultPuffRenRepository(
    private val puffRenRemoteDataSource: PuffRenDataSource,
    private val puffRenLocalDataSource: PuffRenDataSource
): PuffRenRepository{

    override suspend fun login(login: Login): DataResult<LoginResult> {
        return puffRenRemoteDataSource.login(login)
    }


}