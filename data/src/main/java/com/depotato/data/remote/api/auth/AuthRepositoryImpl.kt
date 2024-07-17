package com.depotato.data.remote.api.auth

import com.depotato.data.entity.singleton.Constants.UNKNOWN_ERROR_OCCURRED
import com.depotato.data.local.SharedPref
import com.depotato.data.remote.retrofit.NetRetrofit
import com.depotato.domain.auth.AuthRepository
import com.depotato.domain.auth.login_hisotry.CheckLoginHistoryResult
import com.depotato.domain.auth.sign_in.SignInResult
import com.depotato.domain.equipment.CommonResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val sharedPref: SharedPref
) : AuthRepository {

    private val USER_ID = "userId"
    private val USER_PW = "userPw"

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
                isExist(USER_ID),
                getDataString(USER_ID),
                getDataString(USER_PW)
            )
        }
    }

    override fun logout(): CommonResult {
        try {
            with(sharedPref){
                removeData(USER_ID)
                removeData(USER_PW)
            }
            return CommonResult.Success("")
        }catch (e: Exception){
            return CommonResult.Failure("")
        }
    }

    private fun saveLoginData(userId: String, userPw: String) {
        with(sharedPref) {
            saveData(USER_ID, userId)
            saveData(USER_PW, userPw)
        }
    }
}