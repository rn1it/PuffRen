package com.rn1.puffren.data

import com.squareup.moshi.Json

data class EventInfo(
    val hasRecord: Int? = null,
    val event: Event? = null,
    val message: String? = null
)

data class Event(
    val eventId: String? = null,
    val title: String? = null,
    val type: Int? = null,
    val description: String? = null,
    @Json(name = "total_prize_left")
    val totalPrizeLeft: Int? = null,
    @Json(name = "start_date")
    val startDate: String? = null,
    @Json(name = "end_date")
    val endDate: String? = null
)

data class Prize(
    @Json(name = "prize_coupon_id")
    val prizeCouponId: String? = null,
    @Json(name = "prize_coupon_title")
    val prizeCouponTitle: String? = null,
    @Json(name = "totlaPrizeLeft")
    val totalPrizeLeft: Int = 0,
    val message: String? = null
)