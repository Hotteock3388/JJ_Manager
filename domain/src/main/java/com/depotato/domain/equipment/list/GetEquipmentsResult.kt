package com.depotato.domain.equipment.list

import com.depotato.domain.equipment.Equipment


sealed class GetEquipmentsResult{
    data class Success(
        val equipments: List<Equipment>,
        val responseMessage: String
    ) : GetEquipmentsResult()
    data class Failure(val errorMessage: String) : GetEquipmentsResult()
}