package com.depotato.jubjub_manager.domain.auth.login_data

import com.depotato.jubjub_manager.domain.auth.AuthRepository
import com.depotato.jubjub_manager.domain.equipment.CommonResult

class DeleteAuthDataUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): CommonResult {
        return authRepository.logOut()
    }
}