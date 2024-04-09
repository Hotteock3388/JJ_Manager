package com.depotato.jubjub_manager.view.add_equipment.category

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Filterable
import android.widget.Spinner
import android.widget.TextView
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
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

