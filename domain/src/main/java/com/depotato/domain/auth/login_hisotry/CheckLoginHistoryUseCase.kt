package com.depotato.domain.auth.login_hisotry

import com.depotato.domain.auth.AuthRepository
import javax.inject.Inject

class CheckLoginHistoryUseCase @Inject constructor (
    private val authRepository: AuthRepository
) {
    operator fun invoke(): CheckLoginHistoryResult {
        return authRepository.checkLoginHistoryExist()
    }

//    operator fun invoke(): CheckLoginHistoryResult {
//        authRepository.checkLoginHistoryExist().let {
//            if(it.isExist){
//                authRepository.signIn(it.userId, it.userPw)
//            }
//        }
//        return authRepository.checkLoginHistoryExist()
//    }
}