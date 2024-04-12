package com.depotato.jubjub_manager.data.remote.retrofit

import com.depotato.jubjub_manager.BuildConfig
import com.depotato.jubjub_manager.data.remote.api.AuthApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetRetrofit {

    // HttpLoggingInterceptor 생성
    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    // OkHttpClient 생성
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor) // 로깅 인터셉터 추가
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // 서버 Base URL 설정
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    private val retrofit = createRetrofitInstance()

    fun getAuthApi(): AuthApi{
        return retrofit.create(AuthApi::class.java)
    }
}
