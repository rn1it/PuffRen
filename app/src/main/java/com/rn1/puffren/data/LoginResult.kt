package com.rn1.puffren.data

data class LoginResult(
    val error: String? = null,
    val accessToken: String? = null,
    val userId: String? = null
)

data class RegistryResult(
    val error: String? = null,
    val accessToken: String? = null,
    val message: String? = null
)
