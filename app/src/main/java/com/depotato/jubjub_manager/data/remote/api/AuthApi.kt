package com.depotato.jubjub_manager.data.remote.api

import com.depotato.jubjub_manager.entity.dataclass.response.SignInResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    // 로그인
    @GET("auth/sign-in")
    fun signIn(
        @Query("user_id") userId: String,
        @Query("user_pw") userPw: String,
    ) : Single<SignInResponse>


}