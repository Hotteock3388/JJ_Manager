package com.depotato.data.remote.api.equipment

import com.depotato.data.remote.api.CommonResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface EquipmentApi {

    @GET("equipment/")
    suspend fun getEquipments(): Result<GetEquipmentsResponse>

    @Multipart
    @POST("equipment/")
    suspend fun addEquipment(
        @Part file: MultipartBody.Part,
        @Part("equipment") equipment: RequestBody
    ): Result<CommonResponse>

    @Multipart
    @PATCH("equipment/")
    suspend fun editEquipmentExcludeImage(
        @Part("equipment") equipment: RequestBody
    ): Result<CommonResponse>

    @Multipart
    @PATCH("equipment/")
    suspend fun editEquipmentIncludeImage(
        @Part file: MultipartBody.Part,
        @Part("equipment") equipment: RequestBody
    ): Result<CommonResponse>


    @GET("equipment/category")
    suspend fun getCategories(): Result<GetCategoriesResponse>

}