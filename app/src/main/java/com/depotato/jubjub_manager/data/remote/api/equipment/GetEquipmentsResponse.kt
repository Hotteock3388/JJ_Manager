package com.depotato.jubjub_manager.data.remote.api.equipment

import com.depotato.jubjub_manager.ui.main.equipment_list.Equipment

data class GetEquipmentsResponse(
    val status: Int,
    val message: String,
    val equipments: List<Equipment>
)
