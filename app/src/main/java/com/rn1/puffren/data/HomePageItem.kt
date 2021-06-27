package com.rn1.puffren.data

import com.squareup.moshi.Json

data class HomePageItem(
    val seq: Int,
    @Json(name = "path")
    val image: String? = null,
    val title: String? = null,
    val entry: Int
)
