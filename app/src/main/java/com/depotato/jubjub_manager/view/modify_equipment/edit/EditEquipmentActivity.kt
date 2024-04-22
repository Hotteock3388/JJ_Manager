package com.depotato.jubjub_manager.view.modify_equipment.edit

import androidx.core.net.toUri
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

    private fun initEquipmentInfo(){

        try {
            val equipment = intent.getSerializableExtra("equipment") as Equipment

            with(viewModel){
                equipmentId = equipment.id
                equipmentImageUrl.value = equipment.imageUrl
                equipmentName.value = equipment.name
                equipmentMaxAmount.value = equipment.maxAmount.toString()
                equipmentCurrentAmount.value = equipment.currentAmount.toString()
                equipmentCategory = equipment.category

                binding.spinnerCategory.setSelection(getCategoryIdx(equipment.category))

                Glide
                    .with(binding.root)
                    .load(equipment.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_add_image)
                    .into(binding.imageViewEquipmentImage)

                equipment.imageUrl.toUri()
            }

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun removeImage(){
        super.removeImage()
        viewModel.equipmentImageUrl.value = ""
    }

    private fun getCategoryIdx(category: String): Int {
        return viewModel.categoryArray.indexOf(category)
    }

    fun editEquipment(){
        //send EquipmentData

        if(viewModel.isEquipmentDataValid()){

            viewModel.editEquipment()
        }

    }
}