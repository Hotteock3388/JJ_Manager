package com.depotato.jubjub_manager.data.remote.api.equipment

data class GetCategoriesResponse(
    val status: Int,
    val message: String,
    val categories: List<String>
)
