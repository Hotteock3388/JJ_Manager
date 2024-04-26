package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.entity.dataclass.response.CheckLoginHistoryResult
import com.depotato.jubjub_manager.entity.dataclass.response.SignInResult
import io.reactivex.Observable

interface AuthRepository {
    fun checkLoginHistoryExist(): CheckLoginHistoryResult
//    fun saveLoginData(userId: String, userPw: String)
    fun signIn(userId: String, userPw: String): Observable<SignInResult>
}