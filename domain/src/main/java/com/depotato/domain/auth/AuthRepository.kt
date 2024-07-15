package com.depotato.domain.auth

import com.depotato.domain.auth.login_hisotry.CheckLoginHistoryResult
import com.depotato.domain.auth.sign_in.SignInResult
import com.depotato.domain.equipment.CommonResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(userId: String, userPw: String): Flow<SignInResult>
    fun checkLoginHistoryExist(): CheckLoginHistoryResult
    fun logout(): CommonResult
//    fun saveLoginData(userId: String, userPw: String)
}