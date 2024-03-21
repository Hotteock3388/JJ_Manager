package com.depotato.view.equipment_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.databinding.FragmentEquipmentListBinding
import com.depotato.view.equipment_list.adapter.Equipment
import com.depotato.view.equipment_list.adapter.EquipmentListRVAdapter


class EquipmentListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentEquipmentListBinding>(inflater, R.layout.fragment_equipment_list, container, false)

        val dataList = arrayListOf(
            Equipment("애플 iPad Pro 11형", "패드 & 탭", 14, 20, ""),
            Equipment("Macbook 13인치", "노트북", 2, 10, ""),
            Equipment("Magic Mouse 2", "마우스", 10, 10, "")
        )
        val adapter = EquipmentListRVAdapter(dataList)

        binding.recyclerViewEquipmentList.adapter = adapter

        adapter.notifyDataSetChanged()


        return binding.root
    }

}