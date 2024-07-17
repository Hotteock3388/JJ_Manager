package com.depotato.data.remote.api.equipment

import com.depotato.data.entity.singleton.Constants.UNKNOWN_ERROR_OCCURRED
import com.depotato.data.mapper.EquipmentMapper
import com.depotato.data.remote.model.CommonResponse
import com.depotato.data.remote.retrofit.NetRetrofit
import com.depotato.domain.equipment.CommonResult
import com.depotato.domain.equipment.Equipment
import com.depotato.domain.equipment.EquipmentRepository
import com.depotato.domain.equipment.GetCategoryResult
import com.depotato.domain.equipment.list.GetEquipmentsResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class EquipmentRepositoryImpl @Inject constructor(
) : EquipmentRepository {

    override fun getEquipments(): Flow<GetEquipmentsResult> = flow {
        NetRetrofit.getEquipmentApi().getEquipments()
            .onSuccess { response ->
                emit(
                    GetEquipmentsResult.Success(
                        response.equipments,
                        response.message
                    )
                )
            }.onFailure {
                emit(GetEquipmentsResult.Failure(it.message ?: UNKNOWN_ERROR_OCCURRED))
            }
    }

    override fun addEquipment(
        imageFile: MultipartBody.Part,
        equipment: Equipment
    ): Flow<CommonResult> = flow {
        emit(emitCommonResult(
            NetRetrofit.getEquipmentApi().addEquipment(imageFile, getEquipmentRequestBody(equipment))
        ))
    }

    override fun editEquipmentIncludeImage(
        imageFile: MultipartBody.Part,
        equipment: Equipment
    ): Flow<CommonResult> = flow {
        emit(emitCommonResult(
            NetRetrofit.getEquipmentApi().editEquipmentIncludeImage(imageFile, getEquipmentRequestBody(equipment))
        ))
    }

    override fun editEquipmentExcludeImage(
        equipment: Equipment
    ): Flow<CommonResult> = flow {
        emit(emitCommonResult(
            NetRetrofit.getEquipmentApi().editEquipmentExcludeImage(getEquipmentRequestBody(equipment))
        ))
    }


    private fun getEquipmentRequestBody(equipment: Equipment): RequestBody {
        return Gson().toJson(EquipmentMapper().equipmentToSerializedEquipment(equipment))
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    override fun getCategories(): Flow<GetCategoryResult> = flow {
        NetRetrofit.getEquipmentApi().getCategories()
            .onSuccess { response ->
                emit(
                    GetCategoryResult.Success(response.categories)
                )
            }.onFailure {
                emit(
                    GetCategoryResult.Failure(it.message ?: UNKNOWN_ERROR_OCCURRED)
                )
            }
    }

    private fun emitCommonResult(response: Result<CommonResponse>): CommonResult {
        return try {
            CommonResult.Success(response.getOrThrow().message)
        } catch (e: Exception) {
            e.printStackTrace()
            CommonResult.Failure(e.message ?: UNKNOWN_ERROR_OCCURRED)
        }
    }

}