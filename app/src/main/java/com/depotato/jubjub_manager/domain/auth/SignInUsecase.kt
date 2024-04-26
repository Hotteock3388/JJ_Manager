package com.depotato.jubjub_manager.domain.auth

import com.depotato.jubjub_manager.entity.dataclass.response.SignInResult
import io.reactivex.Observable

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(userId: String, userPw: String): Observable<SignInResult> {
        return authRepository.signIn(userId, userPw)
    }
}