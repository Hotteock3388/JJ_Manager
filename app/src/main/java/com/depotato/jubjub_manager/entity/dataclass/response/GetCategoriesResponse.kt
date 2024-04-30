package com.depotato.jubjub_manager.entity.dataclass.response

data class GetCategoriesResponse(
    val status: Int,
    val message: String,
    val categories: Array<String>
)
