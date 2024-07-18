package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.data.local.SharedPref
import com.depotato.jubjub_manager.data.remote.retrofit.NetRetrofit
import com.depotato.jubjub_manager.domain.auth.login_data.CheckAuthDataResult
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInResult
import com.depotato.jubjub_manager.domain.equipment.CommonResult
import io.reactivex.Observable

class AuthRepositoryImpl(
    private val sharedPref: SharedPref
) : AuthRepository {
    private val USER_ID = "userId"
    private val USER_PW = "userPw"

    override fun checkLoginHistoryExist(): CheckAuthDataResult {
        with(sharedPref) {
            return CheckAuthDataResult(
                isExist(USER_ID),
                getDataString(USER_ID),
                getDataString(USER_PW)
            )
        }
    }

    private fun saveLoginData(userId: String, userPw: String) {
        with(sharedPref) {
            saveData(USER_ID, userId)
            saveData(USER_PW, userPw)
        }
    }

    override fun signIn(userId: String, userPw: String): Observable<SignInResult> {
        return NetRetrofit.getAuthApi().signIn(
            userId,
            userPw,
        ).map { response ->
            if (response.status == 200) {
                saveLoginData(userId, userPw)
                SignInResult.Success(response.message)
            } else {
                SignInResult.Failure(response.message)
            }
        }.onErrorReturn { error ->
            SignInResult.Failure(error.message ?: "Unknown error")
        }
    }

    override fun logOut(): CommonResult {
        try {
            sharedPref.removeData(USER_ID)
            sharedPref.removeData(USER_PW)
            return CommonResult.Success("로그아웃 성공")
        }catch (e: Exception){
            return CommonResult.Failure("로그아웃 실패, ${e.message}")
        }
    }

}