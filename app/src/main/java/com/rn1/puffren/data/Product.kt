package com.rn1.puffren.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: String,
    val title: String? = null,
    val delivery: Int? = null,
    val price: Int? = null,
    val ingredient: String? = null,
    val description: String? = null,
    val note: String? = null,
    val status: Int? = 1,
    @Json(name = "path")
    val image: String? = null
): Parcelable