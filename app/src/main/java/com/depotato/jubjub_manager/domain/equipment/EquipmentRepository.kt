package com.depotato.jubjub_manager.domain.equipment

import io.reactivex.Observable

interface EquipmentRepository {

    fun getEquipments(): Observable<GetEquipmentsResult>
    fun addEquipment(): CommonResult
    fun editEquipmentIncludeImage(): CommonResult
    fun editEquipmentExcludeImage(): CommonResult

}

sealed class CommonResult{
    data class Success(val responseMessage: String) : CommonResult()
    data class Failure(val errorMessage: String) : CommonResult()
}

sealed class AddEquipmentResult{
    data class Success(val responseMessage: String) : AddEquipmentResult()
    data class Failure(val errorMessage: String) : AddEquipmentResult()
}


sealed class EditEquipmentResult{
    data class Success(val responseMessage: String) : EditEquipmentResult()
    data class Failure(val errorMessage: String) : EditEquipmentResult()
}
