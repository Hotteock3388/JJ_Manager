package com.depotato.jubjub_manager.view.modify_equipment

import android.app.Activity
import android.content.Intent
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
import com.depotato.jubjub_manager.view.modify_equipment.edit.EditEquipmentViewModel
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
            viewModel._equipmentImageUri.value = result.data?.data
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

    override fun initLiveData() {
        viewModel.equipmentImageUri.observe(this){
            if(it == null){
                onImageRemoved()
            }else{
                viewModel.equipmentImageFile = UriConverter().getFileFromUri(contentResolver, it)
                onImageAdded()
            }
        }
        viewModel.getCategoriesComplete.observe(this){
            initCategorySpinner()
            if(viewModel.equipmentCategory.isNotBlank()){
                spinnerCategory.setSelection((viewModel as EditEquipmentViewModel).getCategoryIdx())
            }
        }
        viewModel.addComplete.observe(this){
            finish()
        }
    }

    fun onSpinnerItemSelected(parent: AdapterView<*>?, position: Int) {
        with(viewModel){
            equipmentCategory = if(position == 0){
                ""
            }else{
                categories[position]
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
        viewModel._equipmentImageUri.value = null
    }

    private fun initCategorySpinner(){
        spinnerCategory.adapter = CategorySpinnerAdapter(this, viewModel.categories)
    }

}