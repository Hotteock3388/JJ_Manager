package com.depotato.jubjub_manager.view.modify_equipment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.function_module.UriConverter
import com.depotato.jubjub_manager.view.modify_equipment.category.CategorySpinnerAdapter
import com.depotato.jubjub_manager.view.modify_equipment.image.ImageAddState

abstract class ModifyEquipmentBaseActivity<B : ViewDataBinding, VM : ModifyEquipmentViewModel>(
    resId: Int,
    className: String
) : BaseActivity<B, VM>(resId, className) {

    abstract override val viewModel: VM

    private lateinit var imageViewEquipmentImage: ImageView
    private lateinit var imageViewDeleteImage: ImageView
    private lateinit var spinnerCategory: Spinner


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
        initViews()
        initCategorySpinner()
    }

    private fun initViews() {
        imageViewEquipmentImage = binding.root.findViewById(R.id.imageView_equipmentImage)
        imageViewDeleteImage = binding.root.findViewById(R.id.imageView_deleteImage)
        spinnerCategory = binding.root.findViewById(R.id.spinner_category)
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

        viewModel.equipmentMaxAmount.observe(this){
            viewModel.equipmentCurrentAmount.value = it
        }

        viewModel.addComplete.observe(this){
            finish()
        }

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
        imageViewEquipmentImage.setImageResource(R.drawable.ic_add_image)
    }
    private fun setEquipmentImage(){
        imageViewEquipmentImage.setImageURI(viewModel.imageUri.value)
    }

    private fun activateRemoveButton(){
        imageViewDeleteImage.visibility = View.VISIBLE
    }
    private fun deactivateRemoveButton(){
        imageViewDeleteImage.visibility = View.GONE
    }

    private fun setEquipmentBGWhite(){
        imageViewEquipmentImage.setBackgroundResource(R.drawable.bg_equipment_image)
    }
    private fun setEquipmentBGGray(){
        imageViewEquipmentImage.setBackgroundResource(R.drawable.bg_add_image)
    }


    fun removeImage(){
        // X 아이콘의 온클릭 이벤트 정의
        viewModel.imageUri.value = null
    }

    private fun initCategorySpinner(){
        //스피너 초기화
        spinnerCategory.adapter = CategorySpinnerAdapter(this, viewModel.categoryArray)

//        EquipmentCategorySpinner().initCategorySpinner(this, binding.spinnerCategory, viewModel.categoryArray)
    }


}