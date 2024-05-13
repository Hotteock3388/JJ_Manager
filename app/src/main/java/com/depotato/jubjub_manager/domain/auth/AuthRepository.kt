package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.domain.auth.login_hisotry.CheckLoginHistoryResult
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(userId: String, userPw: String): Flow<SignInResult>
    fun checkLoginHistoryExist(): CheckLoginHistoryResult
//    fun saveLoginData(userId: String, userPw: String)
}