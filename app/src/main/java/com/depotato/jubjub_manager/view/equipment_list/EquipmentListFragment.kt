package com.depotato.jubjub_manager.view.equipment_list

import android.content.Intent
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseFragment
import com.depotato.jubjub_manager.databinding.FragmentEquipmentListBinding
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import com.depotato.jubjub_manager.view.modify_equipment.edit.EditEquipmentActivity
import org.koin.android.ext.android.inject


class EquipmentListFragment : BaseFragment<FragmentEquipmentListBinding, EquipmentListViewModel>(R.layout.fragment_equipment_list, "EquipmentListFragment") {

    override val viewModel: EquipmentListViewModel by inject()

    override fun init() {
        setRecyclerViewAdapter()
        viewModel.getEquipments()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEquipments()
    }

    private fun setRecyclerViewAdapter() {
        binding.recyclerViewEquipmentList.adapter = viewModel.adapter
    }

    override fun initLiveData() {

        viewModel.getEquipmentsComplete.observe(this){
            viewModel.adapter.updateItems(viewModel.equipmentsList)
        }

        viewModel.searchText.observe(this){
            viewModel.adapter.filter.filter(it)
        }

        viewModel.onEquipmentItemClick.observe(this){
            onEquipmentItemClick(it)
        }
    }

    private fun onEquipmentItemClick(equipment: Equipment?){
        Intent(requireContext(), EditEquipmentActivity::class.java).apply {
            putExtra("equipment", equipment)
            startActivity(this)
        }
    }

}