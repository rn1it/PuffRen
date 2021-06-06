package com.rn1.puffren.data

import com.squareup.moshi.Json

data class OpenInfo(
    val details: List<ReportOpenStatus>,
    val isStillOpen: Boolean? = null,
    @Json(name = "open_date")
    val openDate: String? = null,
    @Json(name="report_status")
    val reportStatus: Int? = null,
    val isOnBreak: Boolean? =  null
)

data class OverdueInfo(
    val details: List<ReportOpenStatus>,
    @Json(name = "open_date")
    val openDate: String,
)

data class ReportStatus(
    val today: OpenInfo,
    val overdueRecords: List<OverdueInfo>
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

data class ReportDetail(
    val id: String? = null,
    @Json(name = "open_date")
    val openDate: String? = null,
    @Json(name = "report_status")
    val reportStatus: Int? = null,
    val sales: Int? = null,
    val weather: String? = null,
    val details: List<ReportItem>? = null,
    val items: List<ReportItem>? = null
)

data class ReportResult(
    val message: String
)