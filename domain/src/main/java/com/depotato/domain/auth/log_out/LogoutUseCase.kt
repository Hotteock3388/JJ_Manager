package com.depotato.domain.auth.log_out

import com.depotato.domain.auth.AuthRepository
import com.depotato.domain.equipment.CommonResult
import javax.inject.Inject

class LogoutUseCase @Inject constructor (
    private val authRepository: AuthRepository
) {
    operator fun invoke(): CommonResult {
        return authRepository.logout()
    }

}