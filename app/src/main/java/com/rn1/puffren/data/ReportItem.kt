package com.rn1.puffren.data

import com.squareup.moshi.Json

data class ReportItem(
    val id: String? = null,
    @Json(name = "item_title")
    val title: String? = null,
    val category: Int? = null,
    var quantity: Int = 0,
    val price: Int = 0
)