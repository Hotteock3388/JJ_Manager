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

    var equipmentArray: Array<Equipment> = arrayOf()
    private var filteredArray = equipmentArray.copyOf()

    override fun getItemCount(): Int {
        return filteredArray.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = DataBindingUtil.inflate<LayoutEquipmentListItemBinding>(LayoutInflater.from(parent.context), R.layout.layout_equipment_list_item, parent, false)
        return ViewHolder(b, _event)
    }

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        holder.bind(filteredArray[itemPosition])
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

    fun updateItems(newEquipmentArray: Array<Equipment>){
        checkDataDiff(newEquipmentArray)
        updateEquipmentArray(newEquipmentArray)
    }

    private fun checkDataDiff(newEquipmentArray: Array<Equipment>){
        val diffCallback = EquipmentArrayDiffCallback(equipmentArray, newEquipmentArray)
        val diffResult = DiffUtil.calculateDiff(diffCallback) // 계산
        diffResult.dispatchUpdatesTo(this) // 리사이클러뷰 갱신!
    }

    private fun updateEquipmentArray(newEquipmentArray: Array<Equipment>){
        equipmentArray = newEquipmentArray
        filteredArray = equipmentArray.copyOf()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {

                val searchText = constraint.toString().lowercase()

                filteredArray = if (searchText.isEmpty()) {
                    equipmentArray
                } else {
                    arrayListOf<Equipment>().apply {
                        for (equipment in equipmentArray) {
                            if (equipment.name.lowercase().contains(searchText)
                                || equipment.category.lowercase().contains(searchText)) {
                                add(equipment)
                            }
                        }
                    }.toTypedArray()
                }

                return FilterResults().apply {
                    values = filteredArray
                }

            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredArray = results.values as Array<Equipment>
                checkDataDiff(filteredArray)
//                notifyDataSetChanged()
            }
        }
    }
}

