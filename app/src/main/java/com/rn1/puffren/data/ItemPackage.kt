package com.rn1.puffren.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemPackage(
    val quantity: Int = 1,
    var originalPrice: Int = 0,
    @Json(name = "price")
    val onSalePrice: Int = 0
): Parcelable