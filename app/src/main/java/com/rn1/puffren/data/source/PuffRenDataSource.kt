package com.rn1.puffren.data.source

import com.rn1.puffren.data.*

interface PuffRenDataSource {

    suspend fun getHomePageItem(): DataResult<List<HomePageItem>>

    suspend fun login(login: Login): DataResult<LoginResult>

    suspend fun getLoginUser(token: String): DataResult<User>

    suspend fun registry(user:User): DataResult<RegistryResult>

    suspend fun getProductListByType(type: String): DataResult<List<Product>>

    suspend fun getProductDetail(id: String): DataResult<Product>

    suspend fun getReportItems(token: String): DataResult<List<ReportItem>>

    suspend fun getPartnersInfoByDay(day: String): DataResult<List<PartnerInfo>>

    suspend fun getCoupon(token: String, type: String): DataResult<List<Coupon>>

    suspend fun getPerformance(token: String): DataResult<List<Performance>>

    suspend fun reportInAdvance(token: String, reportOpenStatus: ReportOpenStatus): DataResult<ReportResult>

    suspend fun reportForToday(token: String, reportOpenStatus: ReportOpenStatus): DataResult<ReportOpenStatus>

    suspend fun getPartnerLocations(token: String): DataResult<List<String>>

    suspend fun getSaleCalendar(token: String): DataResult<SaleCalendar>

    suspend fun getReportStatus(token: String): DataResult<ReportStatus>

    suspend fun reportSale(token: String, reportDetail: ReportDetail): DataResult<ReportResult>
}