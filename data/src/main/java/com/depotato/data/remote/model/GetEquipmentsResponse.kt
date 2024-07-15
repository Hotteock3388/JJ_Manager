package com.depotato.data.remote.model


data class GetEquipmentsResponse(
    val status: Int,
    val message: String,
    val equipments: List<EquipmentResponse>
)
