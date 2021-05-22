package com.rn1.puffren.data

data class LoginResult(
    val error: String? = null,
    val accessToken: String? = null,
    val userId: String? = null
)
