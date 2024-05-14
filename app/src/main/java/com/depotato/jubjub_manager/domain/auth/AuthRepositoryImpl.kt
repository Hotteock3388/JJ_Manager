package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.data.local.SharedPref
import com.depotato.jubjub_manager.data.remote.retrofit.NetRetrofit
import com.depotato.jubjub_manager.domain.auth.login_hisotry.CheckLoginHistoryResult
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInResult
import com.depotato.jubjub_manager.entity.singleton.Constants
import com.depotato.jubjub_manager.entity.singleton.Constants.UNKNOWN_ERROR_OCCURRED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val sharedPref: SharedPref
) : AuthRepository {

    override fun signIn(userId: String, userPw: String): Flow<SignInResult> = flow {

        NetRetrofit.getAuthApi().signIn(
            userId,
            userPw
        ).onSuccess {
            saveLoginData(userId, userPw)
            emit(SignInResult.Success(it.message))
        }.onFailure {
            emit(SignInResult.Failure(it.message ?: UNKNOWN_ERROR_OCCURRED))
        }

    }

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

}