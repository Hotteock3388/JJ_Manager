package com.depotato.jubjub_manager.entity.dataclass.response

data class CheckLoginHistoryResult(
    val isExist: Boolean,
    val userId: String,
    val userPw: String
)