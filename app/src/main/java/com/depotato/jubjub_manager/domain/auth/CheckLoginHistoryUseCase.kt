package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.entity.dataclass.response.CheckLoginHistoryResult

class CheckLoginHistoryUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): CheckLoginHistoryResult {
        return authRepository.checkLoginHistoryExist()
    }
}