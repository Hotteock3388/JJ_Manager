package com.depotato.jubjub_manager.data.remote.api

import com.depotato.jubjub_manager.entity.dataclass.response.CommonResponse
import com.depotato.jubjub_manager.entity.dataclass.response.GetCategoriesResponse
import com.depotato.jubjub_manager.entity.dataclass.response.GetEquipmentsResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface EquipmentApi {

    @GET("equipment/")
    fun getEquipment(): Observable<GetEquipmentsResponse>

    @Multipart
    @POST("equipment/")
    fun addEquipment(
        @Part file: MultipartBody.Part,
        @Part("equipment") equipment: RequestBody
    ): Observable<CommonResponse>

    @Multipart
    @PATCH("equipment/")
    fun editEquipmentExcludeImage(
        @Part("equipment") equipment: RequestBody
    ): Observable<CommonResponse>

    @Multipart
    @PATCH("equipment/")
    fun editEquipmentIncludeImage(
        @Part file: MultipartBody.Part,
        @Part("equipment") equipment: RequestBody
    ): Observable<CommonResponse>


    @GET("equipment/category")
    fun getCategories(): Observable<GetCategoriesResponse>

}