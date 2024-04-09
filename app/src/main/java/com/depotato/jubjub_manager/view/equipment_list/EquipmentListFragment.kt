package com.depotato.jubjub_manager.view.equipment_list

import android.content.Intent
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseFragment
import com.depotato.jubjub_manager.databinding.FragmentEquipmentListBinding
import com.depotato.jubjub_manager.view.edit_equipment.EditEquipmentActivity
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import com.depotato.jubjub_manager.view.equipment_list.adapter.EquipmentItemEventListener
import com.depotato.jubjub_manager.view.equipment_list.adapter.EquipmentListRVAdapter
import org.koin.android.ext.android.inject


class EquipmentListFragment : BaseFragment<FragmentEquipmentListBinding, EquipmentListViewModel>(R.layout.fragment_equipment_list, "EquipmentListFragment") {

    override val viewModel: EquipmentListViewModel by inject()

    val event = object: EquipmentItemEventListener(){
        override fun onItemClick(equipment: Equipment?) {
            onEquipmentItemClick(equipment)
        }
    }

    val adapter = EquipmentListRVAdapter(event)

    override fun init() {
        setRecyclerViewAdapter()
        viewModel.getEquipmentArray()
    }

    private fun setRecyclerViewAdapter() {
        binding.recyclerViewEquipmentList.adapter = adapter
    }

    override fun initLiveData() {

        viewModel.updateEquipmentArrayComplete.observe(this){
            adapter.updateItems(viewModel.equipmentArray)
        }

        viewModel.searchText.observe(this){
            adapter.filter.filter(it)
        }
    }

    private fun onEquipmentItemClick(equipment: Equipment?){
        Intent(requireContext(), EditEquipmentActivity::class.java).apply {
            putExtra("equipment", equipment)
            startActivity(this)
        }
    }

}