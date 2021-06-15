package com.rn1.puffren.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    @Json(name = "username")
    val userName: String? = "No Name",
    @Json(name = "fullname")
    val fullName: String? = null,
    val connection: String? = null,
    val level: String? = null,
    @Json(name = "amount_spent")
    val amountSpent: Int? = null,
    @Json(name = "partner_id")
    val partnerId: String? = null,
    val score: Int? = null,

    // 0:member ; 1:partner
    @Json(name = "is_partner")
    val isPartner: Int? = 0,

    val phone: String? = null,
    val address: String? = null,
    val city: String? = null

): Parcelable {
    val isVendor: Boolean
        get() = when(isPartner){
            1 -> true
            else -> false
        }
}
