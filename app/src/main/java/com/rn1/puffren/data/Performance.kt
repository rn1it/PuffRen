package com.rn1.puffren.data

import com.squareup.moshi.Json

data class Performance(
    val level: String? = null,
    val score: Int? = null,
    @Json(name = "created_at")
    val createdAt: String? = null
)
