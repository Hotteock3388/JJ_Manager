package com.depotato.jubjub_manager.domain.auth.login_data

data class CheckAuthDataResult(
    val isExist: Boolean,
    val userId: String,
    val userPw: String
)