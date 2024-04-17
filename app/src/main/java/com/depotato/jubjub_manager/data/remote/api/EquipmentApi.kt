package com.depotato.jubjub_manager.data.remote.api

import com.depotato.jubjub_manager.entity.dataclass.response.CommonResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface EquipmentApi {

    @Multipart
    @POST("equipment/")
    fun addEquipment(
        @Part file: MultipartBody.Part,
        @Part("equipment") equipment: RequestBody
    ): Single<CommonResponse>
}