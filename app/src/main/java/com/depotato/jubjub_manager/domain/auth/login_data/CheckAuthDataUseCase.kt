package com.depotato.jubjub_manager.domain.auth.login_data

import com.depotato.jubjub_manager.domain.auth.AuthRepository

class CheckAuthDataUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): CheckAuthDataResult {
        return authRepository.checkLoginHistoryExist()
    }
}