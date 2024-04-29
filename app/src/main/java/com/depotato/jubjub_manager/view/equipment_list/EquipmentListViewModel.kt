package com.depotato.jubjub_manager.view.equipment_list

import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.equipment.GetEquipmentsResult
import com.depotato.jubjub_manager.domain.equipment.GetEquipmentsUseCase
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import com.depotato.jubjub_manager.view.equipment_list.adapter.EquipmentItemEventListener
import com.depotato.jubjub_manager.view.equipment_list.adapter.EquipmentListRVAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EquipmentListViewModel(
    private val getEquipmentsUseCase: GetEquipmentsUseCase
): BaseViewModel("EquipmentListViewModel") {

    var equipmentsList = listOf<Equipment>()

    private val _getEquipmentsCompete = SingleEventLiveData<Unit>()
    val getEquipmentsComplete = _getEquipmentsCompete

    val searchText = MutableLiveData<String>("")

    private val _onEquipmentItemClick = SingleEventLiveData<Equipment>()
    val onEquipmentItemClick: SingleEventLiveData<Equipment> = _onEquipmentItemClick

    val event = object: EquipmentItemEventListener(){
        override fun onItemClick(equipment: Equipment) {
            _onEquipmentItemClick.value = equipment
        }
    }

    val adapter = EquipmentListRVAdapter(event)

    fun getEquipments(){

        addDisposable(
            getEquipmentsUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                   when (it) {
                       is GetEquipmentsResult.Success -> {
                           equipmentsList = it.equipments
                           _getEquipmentsCompete.value = Unit
                       }
                       is GetEquipmentsResult.Failure -> {
                           _toastMessage.value = it.errorMessage
                       }
                   }
                }, {
                    it.printStackTrace()
                    _toastMessage.value = it.localizedMessage
                })
        )

    }

}