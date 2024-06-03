package com.depotato.jubjub_manager.view.modify_equipment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
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
            try {
                viewModel._equipmentImageUri.value = result.data?.data!!
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun init() {
        initViews()
        viewModel.getCategories()
    }

    private fun initViews() {
        imageViewEquipmentImage = binding.root.findViewById(R.id.imageView_equipmentImage)
        imageViewDeleteImage = binding.root.findViewById(R.id.imageView_deleteImage)
        spinnerCategory = binding.root.findViewById(R.id.spinner_category)
    }

    override fun initFlowCollector() {
        with(viewModel){
            collectWhenStarted(equipmentImageUri){
                if(it == Uri.EMPTY){
                    onImageRemoved()
                }else{
                    viewModel.equipmentImageFile = UriConverter().getFileFromUri(contentResolver, it)
                    onImageAdded()
                }
            }

            collectWhenStarted(equipmentMaxAmount){
                viewModel._equipmentCurrentAmount.value = it
            }

            collectWhenStarted(categories){
                initCategorySpinner()
                if(viewModel.__equipmentCategory.isNotBlank()){
                    spinnerCategory.setSelection(viewModel.getCategoryIdx())
                }
            }

            collectWhenStarted(addComplete){
                finish()
            }

        }
    }

    fun onSpinnerItemSelected(parent: AdapterView<*>?, position: Int) {
        with(viewModel){
            __equipmentCategory = if(position == 0){
                ""
            }else{
                categories.value[position]
            }
        }
    }

    fun openGallery(){
        resultLauncher.launch(gallery)
    }

    private fun onImageAdded(){
        setEquipmentImage()
        activateRemoveButton()
        setEquipmentBGWhite()
    }
    private fun onImageRemoved(){
        resetEquipmentImage()
        deactivateRemoveButton()
        setEquipmentBGGray()
    }

    private fun setEquipmentImageViewStatus(currentState: ImageAddState){
        when(currentState){
            ImageAddState.REMOVED -> {
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

    private fun resetEquipmentImage() = imageViewEquipmentImage.setImageResource(R.drawable.ic_add_image)
    private fun setEquipmentImage() = imageViewEquipmentImage.setImageURI(viewModel.equipmentImageUri.value)

    private fun activateRemoveButton() = imageViewDeleteImage.setVisibility(View.VISIBLE)
    private fun deactivateRemoveButton() = imageViewDeleteImage.setVisibility(View.GONE)

    private fun setEquipmentBGWhite() = imageViewEquipmentImage.setBackgroundResource(R.drawable.bg_equipment_image)
    private fun setEquipmentBGGray() = imageViewEquipmentImage.setBackgroundResource(R.drawable.bg_add_image)


    open fun removeImage(){
//        viewModel._equipmentImageUri.value = Uri.EMPTY
        viewModel.deleteImage()
    }

    private fun initCategorySpinner(){
        spinnerCategory.adapter = CategorySpinnerAdapter(this, viewModel.categories.value)
    }

}