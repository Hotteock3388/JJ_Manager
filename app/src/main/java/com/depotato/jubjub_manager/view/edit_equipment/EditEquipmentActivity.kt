package com.depotato.jubjub_manager.view.edit_equipment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.databinding.ActivityEditEquipmentBinding
import com.depotato.jubjub_manager.function_module.UriConverter
import com.depotato.jubjub_manager.view.CategorySpinnerAdapter
import com.depotato.jubjub_manager.view.add_equipment.image.ImageAddState
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import org.koin.android.ext.android.inject

class EditEquipmentActivity : BaseActivity<ActivityEditEquipmentBinding, EditEquipmentViewModel>(R.layout.activity_edit_equipment, "EditEquipmentActivity") {

    override val viewModel: EditEquipmentViewModel by inject()

    private val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){

            //이미지가 Uri 형태로 제공
            val imageUri : Uri? = result.data?.data

            //예외처리 추가 예정
            viewModel.imageUri.value = imageUri

        }
    }

    override fun init() {
        initCategorySpinner()
        initEquipmentInfo()
    }

    override fun initLiveData() {
        viewModel.imageUri.observe(this){

            try {
                if(it == null){
                    showToast("Image deleted")
                }else{
                    viewModel.equipmentImage = UriConverter().getFileFromUri(contentResolver, it!!)
                }

                setEquipmentImageViewStatus(
                    if(it == null) ImageAddState.DELETED else ImageAddState.ADDED
                )

            }catch (e: Exception){
                showToast(e.message.toString())
                //오류 대응 코드 추가
            }

        }
        viewModel.addComplete.observe(this){
            finish()
        }

    }

    private fun initEquipmentInfo(){

        try {
            val equipmentInfo = intent.getSerializableExtra("equipment") as Equipment

            with(viewModel){
                equipmentName.value = equipmentInfo.name
                equipmentMaxAmount.value = equipmentInfo.maxAmount.toString()
                equipmentCategory = equipmentInfo.category

                binding.spinnerCategory.setSelection(getCategoryIdx(equipmentInfo.category))
            }

        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun getCategoryIdx(category: String): Int {
        return viewModel.categoryArray.indexOf(category)
    }

    private fun initCategorySpinner(){
        binding.spinnerCategory.adapter = CategorySpinnerAdapter(this, viewModel.categoryArray)
    }

    fun openGallery(){
        resultLauncher.launch(gallery)
    }

    private fun setEquipmentImageViewStatus(currentState: ImageAddState){
        when(currentState){
            ImageAddState.DELETED -> {
                resetEquipmentImage()
                deactivateRemoveButton()
                setEquipmentBGGray()
            }

            ImageAddState.ADDED -> {
                setEquipmentImage()
                activateRemoveButton()
                setEquipmentBGWhite()
            }
        }
    }

    private fun resetEquipmentImage() {
        binding.imageViewEquipmentImage.setImageResource(R.drawable.ic_add_image)
    }
    private fun setEquipmentImage(){
        binding.imageViewEquipmentImage.setImageURI(viewModel.imageUri.value)
    }

    private fun activateRemoveButton(){
        binding.imageViewDeleteImage.visibility = View.VISIBLE
    }
    private fun deactivateRemoveButton(){
        binding.imageViewDeleteImage.visibility = View.GONE
    }

    private fun setEquipmentBGWhite(){
        binding.imageViewEquipmentImage.setBackgroundResource(R.drawable.bg_equipment_image)
    }
    private fun setEquipmentBGGray(){
        binding.imageViewEquipmentImage.setBackgroundResource(R.drawable.bg_add_image)
    }


    fun removeImage(){
        // X 아이콘의 온클릭 이벤트 정의
        viewModel.imageUri.value = null
    }

    fun editEquipment(){
        //send EquipmentData

        if(viewModel.isEquipmentDataValid()){
            viewModel.addEquipment()
        }

    }
}