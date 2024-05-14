package com.depotato.jubjub_manager.view.modify_equipment.edit

import com.bumptech.glide.Glide
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.databinding.ActivityEditEquipmentBinding
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentBaseActivity
import org.koin.android.ext.android.inject

class EditEquipmentActivity : ModifyEquipmentBaseActivity<ActivityEditEquipmentBinding, EditEquipmentViewModel>(R.layout.activity_edit_equipment, "EditEquipmentActivity") {

    override val viewModel: EditEquipmentViewModel by inject()

    override fun init() {
        super.init()
        initEquipmentInfo()
    }

    override fun initFlowCollector() {
        super.initFlowCollector()
        viewModel.equipmentImageUrl.observe(this){
            if(!it.isNullOrBlank()){
                setEquipmentImage(it)
            }
        }
    }

    private fun setEquipmentImage(imageUrl: String?) {
        Glide.with(binding.root)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_add_image)
            .into(binding.imageViewEquipmentImage)
    }

    private fun initEquipmentInfo(){
        try {
            val equipment = intent.getSerializableExtra("equipment") as Equipment
            viewModel.initEquipmentData(equipment)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun removeImage(){
        super.removeImage()
        viewModel.equipmentImageUrl.value = ""
    }

    fun editEquipment(){
        if(viewModel.isEquipmentDataValid()){
            viewModel.editEquipment()
        }
    }

}