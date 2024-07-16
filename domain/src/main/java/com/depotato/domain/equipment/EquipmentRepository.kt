package com.depotato.domain.equipment

import com.depotato.domain.equipment.list.GetEquipmentsResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface EquipmentRepository {
    fun getEquipments(): Flow<GetEquipmentsResult>
    fun addEquipment(imageFile: MultipartBody.Part, equipment: Equipment): Flow<CommonResult>
    fun editEquipmentIncludeImage(imageFile: MultipartBody.Part, equipment: Equipment): Flow<CommonResult>
    fun editEquipmentExcludeImage(equipment: Equipment): Flow<CommonResult>

    fun getCategories(): Flow<GetCategoryResult>
}

sealed class CommonResult{
    data class Success(val responseMessage: String) : CommonResult()
    data class Failure(val errorMessage: String) : CommonResult()
}

sealed class GetCategoryResult{
    data class Success(val categories: List<String>) : GetCategoryResult()
    data class Failure(val errorMessage: String) : GetCategoryResult()
}
