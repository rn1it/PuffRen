package com.rn1.puffren.data

import com.squareup.moshi.Json

data class Achievement(
    @Json(name = "event_id")
    val id: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val goal: Int? = null,
    @Json(name = "start_date")
    val startDate: String? = null,
    @Json(name = "end_date")
    val endDate: String? = null,
    @Json(name = "reward_coupon_title")
    val rewardCouponTitle: String? = null,
    @Json(name = "coupon_quantity")
    val couponQuantity: Int? = null,
    val progress: Int? = null,
    @Json(name = "reward_received")
    val rewardReceived: Int? = null
)