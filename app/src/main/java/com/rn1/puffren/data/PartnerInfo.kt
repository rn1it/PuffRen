package com.rn1.puffren.data

import com.google.android.gms.maps.model.LatLng
import com.rn1.puffren.util.Util
import com.squareup.moshi.Json

data class PartnerInfo(
    @Json(name = "expected_open_time")
    val expectedOpenTime: String? = null,
    @Json(name = "open_location")
    val openLocation: String? = null,
    @Json(name = "open_status")
    val openStatus: Int? = null,
    val level: String? = null,
    val lat: Double? = null,
    val lng: Double? = null
){
    val latLng: LatLng?
        get() = Util.transferToLatLng(lat, lng, openLocation)
}
