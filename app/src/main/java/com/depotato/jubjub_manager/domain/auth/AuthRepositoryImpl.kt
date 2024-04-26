package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.data.local.SharedPref
import com.depotato.jubjub_manager.data.remote.retrofit.NetRetrofit
import com.depotato.jubjub_manager.entity.dataclass.response.CheckLoginHistoryResult
import com.depotato.jubjub_manager.entity.dataclass.response.SignInResult
import com.depotato.jubjub_manager.entity.singleton.Constants
import io.reactivex.Observable

class AuthRepositoryImpl(
    private val sharedPref: SharedPref
) : AuthRepository {

    override fun checkLoginHistoryExist(): CheckLoginHistoryResult {
        with(sharedPref) {
            return CheckLoginHistoryResult(
                isExist(Constants.USER_ID),
                getDataString(Constants.USER_ID),
                getDataString(Constants.USER_PW)
            )
        }
    }

    private fun saveLoginData(userId: String, userPw: String) {
        with(sharedPref) {
            saveData(Constants.USER_ID, userId)
            saveData(Constants.USER_PW, userPw)
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

}