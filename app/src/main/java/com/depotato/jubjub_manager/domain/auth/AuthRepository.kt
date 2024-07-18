package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.domain.auth.login_hisotry.CheckLoginHistoryResult
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInResult
import io.reactivex.Observable

interface AuthRepository {
    fun checkLoginHistoryExist(): CheckLoginHistoryResult
    fun signIn(userId: String, userPw: String): Observable<SignInResult>
}