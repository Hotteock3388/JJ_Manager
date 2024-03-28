package com.depotato.jubjub_manager.view.equipment_list.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.databinding.LayoutEquipmentListItemBinding

class EquipmentListRVAdapter(val dataList: ArrayList<Equipment>): RecyclerView.Adapter<EquipmentListRVAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = DataBindingUtil.inflate<LayoutEquipmentListItemBinding>(LayoutInflater.from(parent.context), R.layout.layout_equipment_list_item, parent, false)
        return ViewHolder(b)
    }

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        holder.bind(dataList[itemPosition])
    }

    class ViewHolder(private val binding : LayoutEquipmentListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Equipment){

            binding.data = data

        }
    }
}

data class Equipment(
    val name: String,
    val category: String,
    val currentAmount: Int,
    val maxAmount: Int,
    val imageUrl: String
)
