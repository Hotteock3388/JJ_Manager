package com.depotato.jubjub_manager.view.modify_equipment.edit

import com.depotato.jubjub_manager.data.remote.retrofit.NetRetrofit
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditEquipmentViewModel: ModifyEquipmentViewModel("EditEquipmentViewModel") {


    fun editEquipment() {

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

        if(equipmentImageUri.value == null){
            addDisposable(
                NetRetrofit.getEquipmentApi().editEquipmentExcludeImage(requestBody2)
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
        } else {
            addDisposable(
                NetRetrofit.getEquipmentApi().editEquipmentIncludeImage(multipartFile, requestBody2)
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

}