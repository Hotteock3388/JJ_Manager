package com.depotato.data.remote.model

data class GetCategoriesResponse(
    val status: Int,
    val message: String,
    val categories: List<String>
)
