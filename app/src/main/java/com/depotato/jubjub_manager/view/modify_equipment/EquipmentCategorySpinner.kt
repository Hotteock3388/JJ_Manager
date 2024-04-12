package com.depotato.jubjub_manager.view.modify_equipment

import android.content.Context
import android.widget.Spinner
import com.depotato.jubjub_manager.view.CategorySpinnerAdapter

class EquipmentCategorySpinner() {

    fun initCategorySpinner(context: Context, spinner: Spinner, categoryArray: Array<String>){
        //스피너 초기화
        with(spinner) {
            adapter = CategorySpinnerAdapter(context, categoryArray)

//            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(p0: AdapterView<*>?) {}
//                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                }
//            }
        }
    }
}

