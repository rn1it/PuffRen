package com.rn1.puffren.data.source.remote

import com.rn1.puffren.R
import com.rn1.puffren.data.*
import com.rn1.puffren.data.source.PuffRenDataSource
import com.rn1.puffren.network.PuffrenApi
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.Util.getString
import com.rn1.puffren.util.Util.isInternetConnected

object PuffRenRemoteDataSource: PuffRenDataSource {

    override suspend fun getHomePageItem(): DataResult<List<HomePageItem>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = PuffrenApi.retrofitService.getHomePageItem()
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun login(login: Login): DataResult<LoginResult> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.login(login)

            listResult.error?.let {
                return DataResult.Fail(it)
            }
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getLoginUser(token: String): DataResult<User> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getLoginUser(token)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun registry(user: User): DataResult<RegistryResult> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.registry(user)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getProductListByType(type: String): DataResult<List<Product>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getProductListByType(type)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getProductDetail(id: String): DataResult<Product> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getProductDetail(id)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getReportItems(token: String): DataResult<List<ReportItem>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getReportItems(token)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getPartnersInfoByDay(day: String): DataResult<List<PartnerInfo>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getPartnersInfoByDay(day)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getCoupon(token: String, type: String): DataResult<List<Coupon>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getCoupon(token, type)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getPerformance(token: String): DataResult<List<Performance>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getPerformance(token)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun reportInAdvance(
        token: String,
        reportOpenStatus: ReportOpenStatus
    ): DataResult<ReportResult> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.reportInAdvance(token, reportOpenStatus)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun reportForToday(
        token: String,
        reportOpenStatus: ReportOpenStatus
    ): DataResult<ReportOpenStatus> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.reportForToday(token, reportOpenStatus)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getPartnerLocations(token: String): DataResult<List<String>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getPartnerLocations(token)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getSaleCalendar(token: String): DataResult<SaleCalendar> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getSaleCalendar(token)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getReportStatus(token: String): DataResult<ReportStatus> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getReportStatus(token)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun reportSale(
        token: String,
        reportDetail: ReportDetail
    ): DataResult<ReportResult> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.reportSale(token, reportDetail)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }

    override suspend fun getMemberAchievement(token: String): DataResult<List<Achievement>> {
        if (!isInternetConnected()) {
            return DataResult.Fail(getString(R.string.internet_not_connected))
        }

        return try {

            val listResult = PuffrenApi.retrofitService.getMemberAchievement(token)
            DataResult.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            DataResult.Error(e)
        }
    }
}