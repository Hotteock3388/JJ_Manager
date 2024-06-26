package com.depotato.jubjub_manager.domain.equipment.list

import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment

sealed class GetEquipmentsResult{
    data class Success(
        val equipments: Array<Equipment>,
        val responseMessage: String
    ) : GetEquipmentsResult()
    data class Failure(val errorMessage: String) : GetEquipmentsResult()
}