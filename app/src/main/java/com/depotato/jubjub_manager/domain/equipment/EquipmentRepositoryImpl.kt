package com.depotato.jubjub_manager.domain.equipment

import com.depotato.jubjub_manager.data.remote.retrofit.NetRetrofit
import io.reactivex.Observable

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

    override fun addEquipment(): CommonResult {

    }

    override fun editEquipmentIncludeImage(): CommonResult {

    }

    override fun editEquipmentExcludeImage(): CommonResult {

    }
}