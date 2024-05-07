package com.depotato.jubjub_manager.data.remote.api.auth

import com.depotato.jubjub_manager.data.remote.api.CommonResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    // 로그인
    @GET("auth/sign-in")
    fun signIn(
        @Query("user_id") userId: String,
        @Query("user_pw") userPw: String,
    ) : Observable<CommonResponse>


}