package com.depotato.jubjub_manager.view.modify_equipment.add

import com.depotato.jubjub_manager.data.remote.retrofit.NetRetrofit
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddEquipmentViewModel: ModifyEquipmentViewModel("AddEquipmentViewModel") {

    fun addEquipment() {

        val equipmentImageRequestBody: RequestBody = RequestBody.create(
            "image/${equipmentImage.extension}".toMediaTypeOrNull(),
            equipmentImage
        )

        val multipartFile = MultipartBody.Part.createFormData(
            "image",
            equipmentImage.name,
            equipmentImageRequestBody
        )

        val requestBody2: RequestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            Gson().toJson(createEquipmentObject())
        )

        addDisposable(
            NetRetrofit.getEquipmentApi().addEquipment(multipartFile, requestBody2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    _toastMessage.value = it.message

                    if(it.status == 200){
                        addComplete.value = Unit
                    }

                },{

                    _toastMessage.value = it.localizedMessage
                    it.printStackTrace()

                })
        )

    }

}