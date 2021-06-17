package com.rn1.puffren.data.source

import com.rn1.puffren.data.*

class DefaultPuffRenRepository(
    private val puffRenRemoteDataSource: PuffRenDataSource,
    private val puffRenLocalDataSource: PuffRenDataSource
): PuffRenRepository{

    override suspend fun getHomePageItem(): DataResult<List<HomePageItem>> {
        return puffRenRemoteDataSource.getHomePageItem()
    }

    override suspend fun login(login: Login): DataResult<LoginResult> {
        return puffRenRemoteDataSource.login(login)
    }

    override suspend fun getLoginUser(token: String): DataResult<User> {
        return puffRenRemoteDataSource.getLoginUser(token)
    }

    override suspend fun registry(user: User): DataResult<RegistryResult> {
        return puffRenRemoteDataSource.registry(user)
    }

    override suspend fun getProductListByType(type: String): DataResult<List<Product>> {
        return puffRenRemoteDataSource.getProductListByType(type)
    }

    override suspend fun getProductDetail(id: String): DataResult<Product> {
        return puffRenRemoteDataSource.getProductDetail(id)
    }

    override suspend fun getReportItems(token: String): DataResult<List<ReportItem>> {
        return puffRenRemoteDataSource.getReportItems(token)
    }

    override suspend fun getPartnersInfoByDay(day: String): DataResult<List<PartnerInfo>> {
        return puffRenRemoteDataSource.getPartnersInfoByDay(day)
    }

    override suspend fun getCoupon(token: String, type: String): DataResult<List<Coupon>> {
        return puffRenRemoteDataSource.getCoupon(token, type)
    }

    override suspend fun getPerformance(token: String): DataResult<List<Performance>> {
        return puffRenRemoteDataSource.getPerformance(token)
    }

    override suspend fun reportInAdvance(
        token: String,
        reportOpenStatus: ReportOpenStatus
    ): DataResult<ReportResult> {
        return puffRenRemoteDataSource.reportInAdvance(token, reportOpenStatus)
    }

    override suspend fun reportForToday(
        token: String,
        reportOpenStatus: ReportOpenStatus
    ): DataResult<ReportOpenStatus> {
        return puffRenRemoteDataSource.reportForToday(token, reportOpenStatus)
    }

    override suspend fun getPartnerLocations(token: String): DataResult<List<String>> {
        return puffRenRemoteDataSource.getPartnerLocations(token)
    }

    override suspend fun getSaleCalendar(token: String): DataResult<SaleCalendar> {
        return puffRenRemoteDataSource.getSaleCalendar(token)
    }

    override suspend fun getReportStatus(token: String): DataResult<ReportStatus> {
        return puffRenRemoteDataSource.getReportStatus(token)
    }

    override suspend fun reportSale(
        token: String,
        reportDetail: ReportDetail
    ): DataResult<ReportResult> {
        return puffRenRemoteDataSource.reportSale(token, reportDetail)
    }

    override suspend fun getMemberAchievement(token: String): DataResult<List<Achievement>> {
        return puffRenRemoteDataSource.getMemberAchievement(token)
    }

    override suspend fun getEventInfo(token: String, eventType: String): DataResult<EventInfo> {
        return puffRenRemoteDataSource.getEventInfo(token, eventType)
    }

    override suspend fun getPrize(
        token: String,
        eventType: String,
        eventId: String
    ): DataResult<Prize> {
        return puffRenRemoteDataSource.getPrize(token, eventType, eventId)
    }

    override suspend fun updateUser(token: String, user: User): DataResult<UpdateUserResult> {
        return puffRenRemoteDataSource.updateUser(token, user)
    }

    override suspend fun getFoodCart(loginResult: LoginResult): DataResult<FoodCartResult> {
        return puffRenRemoteDataSource.getFoodCart(loginResult)
    }
}