package com.rn1.puffren.data.source.local

import com.rn1.puffren.data.*
import com.rn1.puffren.data.source.PuffRenDataSource

class PuffRenLocalDataSource: PuffRenDataSource {
    override suspend fun getHomePageItem(): DataResult<List<HomePageItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun login(login: Login): DataResult<LoginResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getLoginUser(token: String): DataResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun registry(user: User): DataResult<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductListByType(type: String): DataResult<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetail(id: String): DataResult<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getReportItems(token: String): DataResult<List<ReportItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPartnersInfoByDay(day: String): DataResult<List<PartnerInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCoupon(token: String, type: String): DataResult<List<Coupon>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPerformance(token: String): DataResult<List<Performance>> {
        TODO("Not yet implemented")
    }

    override suspend fun reportInAdvance(token: String, reportOpenStatus: ReportOpenStatus): DataResult<ReportResult> {
        TODO("Not yet implemented")
    }

    override suspend fun reportForToday(token: String, reportOpenStatus: ReportOpenStatus): DataResult<ReportOpenStatus> {
        TODO("Not yet implemented")
    }

    override suspend fun getPartnerLocations(token: String): DataResult<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaleCalendar(token: String): DataResult<SaleCalendar> {
        TODO("Not yet implemented")
    }

    override suspend fun getReportStatus(token: String): DataResult<ReportStatus> {
        TODO("Not yet implemented")
    }

    override suspend fun reportSale(
        token: String,
        reportDetail: ReportDetail
    ): DataResult<ReportResult> {
        TODO("Not yet implemented")
    }
}