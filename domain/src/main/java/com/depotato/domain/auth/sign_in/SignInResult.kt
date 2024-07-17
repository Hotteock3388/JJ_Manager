package com.depotato.domain.auth.sign_in

sealed class SignInResult {
    data class Success(val responseMessage: String) : SignInResult()
    data class Failure(val errorMessage: String) : SignInResult()
}
