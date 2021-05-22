package com.rn1.puffren.data

import com.google.android.gms.maps.model.LatLng
import com.rn1.puffren.util.Util

data class PartnerInfo(
    val expected_open_time: String? = null,
    val open_location: String? = null,
    val open_status: Int? = null,
    val level: String? = null
){
    val latLng: LatLng?
        get() = Util.transferToLatLng(open_location)
}
