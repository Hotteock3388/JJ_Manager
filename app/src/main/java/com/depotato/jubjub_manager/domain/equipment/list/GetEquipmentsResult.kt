package com.depotato.jubjub_manager.domain.equipment.list

import com.depotato.jubjub_manager.ui.main.equipment_list.Equipment

sealed class GetEquipmentsResult{
    data class Success(
        val equipments: List<Equipment>,
        val responseMessage: String
    ) : GetEquipmentsResult()
    data class Failure(val errorMessage: String) : GetEquipmentsResult()
}