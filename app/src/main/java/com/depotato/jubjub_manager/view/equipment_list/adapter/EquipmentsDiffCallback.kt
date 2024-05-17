package com.depotato.jubjub_manager.view.equipment_list.adapter

import androidx.recyclerview.widget.DiffUtil

class EquipmentsDiffCallback(
    private val oldList: List<Equipment>,
    private val newList: List<Equipment>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        // 기존 리스트의 크기 반환
        return oldList.size
    }

    override fun getNewListSize(): Int {
        // 새 리스트의 크기 반환
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // 두 아이템이 같은 객체인가? 반환
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.javaClass == newItem.javaClass
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // 같은 데이터를 갖고 있는가? 반환
        // areItemsTheSame이 true이면 이 함수가 호출된다.
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.name == newItem.name &&
                oldItem.category == newItem.category &&
                oldItem.imageUrl == newItem.imageUrl &&
                oldItem.maxAmount == newItem.maxAmount &&
                oldItem.currentAmount == newItem.currentAmount
    }
}
