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

    var equipments: List<Equipment> = listOf()
    private var filteredEquipments = equipments.map { it.copy() }

    override fun getItemCount(): Int {
        return filteredEquipments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = DataBindingUtil.inflate<LayoutEquipmentListItemBinding>(LayoutInflater.from(parent.context), R.layout.layout_equipment_list_item, parent, false)
        return ViewHolder(b, _event)
    }

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        holder.bind(filteredEquipments[itemPosition])
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

    fun updateItems(newEquipments: List<Equipment>){
        val diffCallback = EquipmentsDiffCallback(equipments, newEquipments)
        val diffResult = DiffUtil.calculateDiff(diffCallback) // 계산
        diffResult.dispatchUpdatesTo(this) // 리사이클러뷰 갱신!
        updateEquipments(newEquipments)
    }
    private fun updateEquipments(newEquipments: List<Equipment>){
        equipments = newEquipments
        filteredEquipments = equipments.map { it.copy() }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val searchText = constraint.toString().lowercase()

                filteredEquipments = if (searchText.isEmpty()) {
                    equipments
                } else {
                    arrayListOf<Equipment>().apply {
                        for (equipment in equipments) {
                            if (equipment.name.lowercase().contains(searchText)
                                || equipment.category.lowercase().contains(searchText)) {
                                add(equipment)
                            }
                        }
                    }.toList()
                }

                return FilterResults().apply {
                    values = filteredEquipments
                }
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredEquipments = results.values as List<Equipment>
                updateItems(filteredEquipments)
            }
        }
    }
}

