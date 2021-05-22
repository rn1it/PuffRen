package com.rn1.puffren.network

import com.rn1.puffren.BuildConfig
import com.rn1.puffren.data.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val HOST_NAME = "puffren.com.tw"
private const val BASE_URL = "https://$HOST_NAME/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = when (BuildConfig.LOGGER_VISIABLE) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    })
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface PuffrenApiService{

    /**
     * Home Page Item
     */
    @GET("application/entryImages")
    suspend fun getHomePageItem(): List<HomePageItem>

    /**
     * User Login and Get Token
     */
    @POST("user/login")
    suspend fun login(@Body login: Login): LoginResult

    /**
     * Get Login User by Token
     */
    @GET("user/profile")
    suspend fun getLoginUser(@Header("Authorization") token: String): User

    /**
     * Member Registry
     */
    @POST("user/register?origin=2")
    suspend fun registry(@Body user:User): String

    /**
     * Product List
     */
    @GET("application/products/{category}")
    suspend fun getProductListByType(@Path("category") id: String): List<Product>

    /**
     * Product Details
     */
    @GET("product/{productId}")
    suspend fun getProductDetail(@Path("productId") id: String): Product

    /**
     * Report Items
     */
    @GET("application/reportItems")
    suspend fun getReportItems(@Header("Authorization") token: String): List<ReportItem>

    /**
     * Partner Map
     */
    @GET("application/partnerMap/{day}")
    suspend fun getPartnersInfoByDay(@Path("day") day: String): List<PartnerInfo>

    /**
     * Member Coupon
     */
    @GET("user/coupon/{validFor}")
    suspend fun getCoupon(@Header("Authorization") token: String, @Path("validFor") type: String): List<Coupon>

    /**
     * Partner Performance
     */
    @GET("partner/performanceHistory")
    suspend fun getPerformance(@Header("Authorization") token: String): List<Performance>
}

object PuffrenApi {
    val retrofitService: PuffrenApiService by lazy { retrofit.create(PuffrenApiService::class.java) }
}