package com.depotato.jubjub_manager.data.remote.api.equipment

import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment

data class GetEquipmentsResponse(
    val status: Int,
    val message: String,
    val equipments: List<Equipment>
)
