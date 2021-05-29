package com.rn1.puffren.data

data class SaleCalendar(
    val expectToOpen: List<ReportOpenStatus>,
    val relax: List<ReportOpenStatus>,
    val reportDetails: List<ReportDetail>
)