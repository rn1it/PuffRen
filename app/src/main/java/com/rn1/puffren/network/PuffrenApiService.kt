package com.rn1.puffren.network

import com.rn1.puffren.BuildConfig
import com.rn1.puffren.data.Login
import com.rn1.puffren.data.LoginResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

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
     * user login
     */
    @POST("user/login")
    suspend fun login(@Body login: Login): LoginResult
}

object PuffrenApi {
    val retrofitService: PuffrenApiService by lazy { retrofit.create(PuffrenApiService::class.java) }
}