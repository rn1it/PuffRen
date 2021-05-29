package com.rn1.puffren.data

import com.squareup.moshi.Json

data class ReportItem(
    val id: String? = null,
    @Json(name = "item_title")
    val title: String? = null,
    val category: Int? = null,
    val quantity: Int? = null
)