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
    val connection: String? = null,
    val level: String? = null,
    @Json(name = "amount_spent")
    val amountSpent: Int? = null,
    @Json(name = "partner_id")
    val partnerId: String? = null,
    val score: Int? = null,

    // 0:guest ; 1:partner
    @Json(name = "is_partner")
    val isPartner: Int? = 0,

    // not sure for what?
    //    val phone: String? = "0912345678",
    //    val address: String? = "台北市"
): Parcelable {
    val isVendor: Boolean
        get() = when(isPartner){
            1 -> true
            else -> false
        }
}
