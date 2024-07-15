package com.depotato.domain.auth.login_hisotry

data class CheckLoginHistoryResult(
    val isExist: Boolean,
    val userId: String,
    val userPw: String
)