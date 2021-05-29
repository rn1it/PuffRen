package com.rn1.puffren.data.source

import com.rn1.puffren.data.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PuffRenDataSource {

    suspend fun getHomePageItem(): DataResult<List<HomePageItem>>

    suspend fun login(login: Login): DataResult<LoginResult>

    suspend fun getLoginUser(token: String): DataResult<User>

    suspend fun registry(user:User): DataResult<String>

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
}