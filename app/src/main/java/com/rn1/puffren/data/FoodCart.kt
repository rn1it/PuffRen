package com.rn1.puffren.data

import com.squareup.moshi.Json

data class FoodCartResult(
    @Json(name = "path")
    val image: String? = null,
    @Json(name = "foodCartSets")
    val foodCartSets: List<FoodCartSet> = listOf(),
    val userInfo: User
)

data class FoodCartSet(
    val id: String,
    val title: String,
    val price: Int
)