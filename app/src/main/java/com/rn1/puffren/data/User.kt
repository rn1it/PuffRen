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
    val userName: String? = null,
    val connection: String? = null,
    val level: Int? = null,
    val amount_spent: Int? = null,
    val partner_id: String? = null,
    val score: Int? = null,

    // not sure for what?
    @Json(name = "fullname")
    val fullName: String? = "王小明",
    val phone: String? = "0912345678",
    val address: String? = "台北市"
): Parcelable {
    val isVendor: Boolean
        get() = when(level){
            null -> true
            else -> false
        }
}
