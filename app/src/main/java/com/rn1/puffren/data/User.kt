package com.rn1.puffren.data

import com.squareup.moshi.Json

data class User(
    val partnerId: String? = "N01",
    @Json(name = "fullname")
    val fullName: String? = "王小明",
    val email: String? = "partner001@gmail.com",
    val phone: String? = "0912345678",
    val address: String? = "台北市"
)
