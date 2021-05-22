package com.rn1.puffren.data

import com.squareup.moshi.Json

data class Coupon(

    @Json(name = "user_coupon_id")
    val id: String? = null,
    val title: String? = null,
    val type: Int? = null,          // coupon 種類
    val usage: Int? = null,         // coupon 用途
    val item: Int? = null,          // coupon 品項
    val quantity: Int? = null,      // 品項數量
    @Json(name = "expire_date")
    val expireDate: String? = null // 過期日
)
