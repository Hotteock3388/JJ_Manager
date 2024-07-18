package com.depotato.jubjub_manager.domain.equipment

import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsResult
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface EquipmentRepository {

    fun getEquipments(): Observable<GetEquipmentsResult>
    fun addEquipment(imageFile: MultipartBody.Part, equipment: RequestBody): Observable<CommonResult>
    fun editEquipmentIncludeImage(imageFile: MultipartBody.Part, equipment: RequestBody): Observable<CommonResult>
    fun editEquipmentExcludeImage(equipment: RequestBody): Observable<CommonResult>

    fun getCategories(): Observable<GetCategoryResult>

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

sealed class GetCategoryResult{
    data class Success(val categories: List<String>) : GetCategoryResult()
    data class Failure(val errorMessage: String) : GetCategoryResult()
}
