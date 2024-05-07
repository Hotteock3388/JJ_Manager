package com.depotato.jubjub_manager.entity.dataclass.response

sealed class SignInResult {
    data class Success(val responseMessage: String) : SignInResult()
    data class Failure(val errorMessage: String) : SignInResult()
}
