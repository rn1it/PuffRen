package com.rn1.puffren.data

import com.squareup.moshi.Json

data class HomePageItem(
    val entry: Int = 0, // product
    @Json(name = "path")
    val image: String? = "https://db8vfxqdtkusy.cloudfront.net/admin/homepage/product.png",
    val title: String? = null
)
