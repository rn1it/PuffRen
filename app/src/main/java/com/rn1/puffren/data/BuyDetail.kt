package com.rn1.puffren.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuyDetail(
    val userNickname: String,
    val coupon: String?,
    val totalAmount: Int,
    val couponDiscount: Int,
    val memberDiscount: Int,
    val amountAfterDiscount: Int
): Parcelable
