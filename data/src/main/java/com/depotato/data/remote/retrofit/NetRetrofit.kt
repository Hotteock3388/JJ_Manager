package com.depotato.data.remote.retrofit

import com.depotato.data.BuildConfig
import com.depotato.data.remote.api.auth.AuthApi
import com.depotato.data.remote.api.equipment.EquipmentApi
import com.depotato.data.remote.retrofit.adapter.ResultCallAdapterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetRetrofit {

    // HttpLoggingInterceptor 생성
    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    // OkHttpClient 생성
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor) // 로깅 인터셉터 추가
        .build()

    private fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // 서버 Base URL 설정
            .client(client)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    private val retrofit = createRetrofitInstance()

    fun getAuthApi(): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    fun getEquipmentApi(): EquipmentApi {
        return retrofit.create(EquipmentApi::class.java)
    }
}
