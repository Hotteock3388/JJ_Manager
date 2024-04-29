package com.depotato.jubjub_manager.view.equipment_list.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.databinding.LayoutEquipmentListItemBinding

class EquipmentListRVAdapter(private val _event: EquipmentItemEventListener) : RecyclerView.Adapter<EquipmentListRVAdapter.ViewHolder>(), Filterable {

    var equipmentList: List<Equipment> = listOf()
    private var filteredList = equipmentList.map { it.copy() }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = DataBindingUtil.inflate<LayoutEquipmentListItemBinding>(LayoutInflater.from(parent.context), R.layout.layout_equipment_list_item, parent, false)
        return ViewHolder(b, _event)
    }

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        holder.bind(filteredList[itemPosition])
    }

    class ViewHolder(
        private val binding : LayoutEquipmentListItemBinding,
        private val _event: EquipmentItemEvent
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(_equipment: Equipment?){
            with(binding){
                equipment = _equipment
                event = _event
            }

            Glide
                .with(binding.root)
                .load(_equipment?.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_add_image)
                .into(binding.imageViewImage)
        }

        interface EquipmentItemEvent{
            fun onItemClick(equipment: Equipment)
        }
    }

    fun updateItems(newEquipmentList: List<Equipment>){
        val diffCallback = EquipmentListDiffCallback(equipmentList, newEquipmentList)
        val diffResult = DiffUtil.calculateDiff(diffCallback) // 계산
        diffResult.dispatchUpdatesTo(this) // 리사이클러뷰 갱신!

        updateEquipmentList(newEquipmentList)
    }
    private fun updateEquipmentList(newEquipmentList: List<Equipment>){
        equipmentList = newEquipmentList
        filteredList = equipmentList.map { it.copy() }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {

                val searchText = constraint.toString().lowercase()

                filteredList = if (searchText.isEmpty()) {
                    equipmentList
                } else {
                    mutableListOf<Equipment>().apply {
                        for (equipment in equipmentList) {
                            if (equipment.name.lowercase().contains(searchText)
                                || equipment.category.lowercase().contains(searchText)) {
                                add(equipment)
                            }
                        }
                    }
                }

                return FilterResults().apply {
                    values = filteredList
                }

            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList = results.values as List<Equipment>
                updateItems(filteredList)
//                notifyDataSetChanged()
            }
        }
    }
}

