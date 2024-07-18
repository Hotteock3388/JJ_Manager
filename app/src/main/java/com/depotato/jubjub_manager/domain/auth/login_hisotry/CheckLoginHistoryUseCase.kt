package com.depotato.jubjub_manager.domain.auth.login_hisotry

import com.depotato.jubjub_manager.domain.auth.AuthRepository

class CheckLoginHistoryUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): CheckLoginHistoryResult {
        return authRepository.checkLoginHistoryExist()
    }
}