package com.rn1.puffren.data.source

import com.rn1.puffren.data.*

interface PuffRenRepository {

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

    suspend fun getMemberAchievement(token: String): DataResult<List<Achievement>>

    suspend fun getEventInfo(token: String, eventType: String): DataResult<EventInfo>

    suspend fun getPrize(token: String, eventType: String, eventId: String): DataResult<Prize>

    suspend fun updateUser(token: String, user: User): DataResult<UpdateUserResult>

    suspend fun getFoodCart(loginResult: LoginResult) : DataResult<FoodCartResult>
}