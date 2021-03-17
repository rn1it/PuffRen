package com.rn1.puffren.data.source

import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Login
import com.rn1.puffren.data.LoginResult

interface PuffRenDataSource {
    suspend fun login(login: Login): DataResult<LoginResult>
}