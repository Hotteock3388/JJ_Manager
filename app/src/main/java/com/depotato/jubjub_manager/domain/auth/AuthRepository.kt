package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.domain.auth.login_data.CheckAuthDataResult
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInResult
import com.depotato.jubjub_manager.domain.equipment.CommonResult
import io.reactivex.Observable

interface AuthRepository {
    fun checkLoginHistoryExist(): CheckAuthDataResult
    fun signIn(userId: String, userPw: String): Observable<SignInResult>
    fun logOut(): CommonResult
}