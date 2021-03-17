package com.rn1.puffren.data.source.local

import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Login
import com.rn1.puffren.data.LoginResult
import com.rn1.puffren.data.source.PuffRenDataSource

class PuffRenLocalDataSource: PuffRenDataSource {
    override suspend fun login(login: Login): DataResult<LoginResult> {
        TODO("Not yet implemented")
    }
}