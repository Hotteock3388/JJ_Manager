package com.depotato.jubjub_manager.domain.auth.sign_in

import com.depotato.jubjub_manager.domain.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor (
    private val authRepository: AuthRepository
) {
    operator fun invoke(userId: String, userPw: String): Flow<SignInResult> {
        return authRepository.signIn(userId, userPw)
    }
}