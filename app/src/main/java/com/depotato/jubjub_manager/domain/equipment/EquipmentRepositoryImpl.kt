package com.depotato.jubjub_manager.domain.equipment

import com.depotato.jubjub_manager.data.remote.retrofit.NetRetrofit
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsResult
import com.depotato.jubjub_manager.entity.dataclass.response.CommonResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EquipmentRepositoryImpl: EquipmentRepository {

    override fun getEquipments(): Observable<GetEquipmentsResult> {
        return NetRetrofit.getEquipmentApi().getEquipment()
            .map { response ->
                if(response.status == 200){
                    GetEquipmentsResult.Success(
                        response.equipments,
                        response.message
                    )
                }else{
                    GetEquipmentsResult.Failure(
                        response.message
                    )
                }
            }
    }

    override fun addEquipment(imageFile: MultipartBody.Part, equipment: RequestBody): Observable<CommonResult> {
        return NetRetrofit.getEquipmentApi().addEquipment(imageFile, equipment)
            .map { response ->
                returnCommonResult(response)
            }
    }

    override fun editEquipmentIncludeImage(imageFile: MultipartBody.Part, equipment: RequestBody): Observable<CommonResult> {
        return NetRetrofit.getEquipmentApi().editEquipmentIncludeImage(imageFile, equipment)
            .map { response ->
                returnCommonResult(response)
            }
    }

    override fun editEquipmentExcludeImage(equipment: RequestBody): Observable<CommonResult> {
        return NetRetrofit.getEquipmentApi().editEquipmentExcludeImage(equipment)
            .map { response ->
                returnCommonResult(response)
            }
    }

    override fun getCategories(): Observable<GetCategoryResult> {
        return NetRetrofit.getEquipmentApi().getCategories()
            .map { response ->
                if(response.status == 200){
                    GetCategoryResult.Success(
                        response.categories
                    )
                }else{
                    GetCategoryResult.Failure(
                        response.message
                    )
                }
            }
    }

    private fun returnCommonResult(response: CommonResponse): CommonResult {
        return if(response.status == 200){
            CommonResult.Success(
                response.message
            )
        }else{
            CommonResult.Failure(
                response.message
            )
        }

    }
}