package com.rn1.puffren.data

import com.squareup.moshi.Json

data class Report(
    val reportId: String
)

data class ReportOpenStatus(
    @Json(name = "report_date")
    val reportDate: String? = null,
    @Json(name = "expected_open_time")
    val expectedOpenTime: String? = null,
    @Json(name = "open_location")
    val openLocation: String? = null,
    @Json(name = "open_status")
    val openStatus: Int? = null,
    val recordId: String? = null,
    @Json(name = "open_time")
    val openTime: String? = null,
    @Json(name = "close_time")
    val closeTime: String? = null,
    @Json(name = "partner_id")
    val partnerId: String? = null
)

data class ReportResult(
    val message: String
)