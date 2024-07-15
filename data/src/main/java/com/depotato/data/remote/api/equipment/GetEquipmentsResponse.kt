package com.depotato.data.remote.api.equipment


data class GetEquipmentsResponse(
    val status: Int,
    val message: String,
    val equipments: List<EquipmentRes>
)
