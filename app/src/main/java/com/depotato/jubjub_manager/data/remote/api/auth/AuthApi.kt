package com.depotato.jubjub_manager.data.remote.api.auth

import com.depotato.jubjub_manager.data.remote.api.CommonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {
    @GET("auth/sign-in")
    suspend fun signIn(
        @Query("user_id") userId: String,
        @Query("user_pw") userPw: String,
    ) : Result<CommonResponse>
}