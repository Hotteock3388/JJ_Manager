package com.depotato.jubjub_manager.domain.auth.sign_in

import com.depotato.jubjub_manager.domain.auth.AuthRepository
import io.reactivex.Observable

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(userId: String, userPw: String): Observable<SignInResult> {
        return authRepository.signIn(userId, userPw)
    }
}