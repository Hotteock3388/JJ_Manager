package com.depotato.jubjub_manager.view.modify_equipment.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filterable
import android.widget.TextView
import com.depotato.jubjub_manager.R

class CategorySpinnerAdapter(private val con: Context, private val dataList: Array<String>) : ArrayAdapter<String>(con, R.layout.layout_spinner_item, dataList),
    Filterable {
    private var inflater: LayoutInflater = LayoutInflater.from(con)

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): String {
        return dataList[position]
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        createView(convertView, parent).apply {
            setCategoryText(this, dataList[position])
            return this
        }
    }
    private fun setCategoryText(view: View, category: String){
        view.findViewById<TextView>(R.id.textView_category).text = category
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        return createView(convertView, parent).apply {
            with(this.tag as ViewHolder){
                bind(dataList[position])
            }
        }
    }

    private fun createView(convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder : ViewHolder

        if(view == null){
            view = inflater.inflate(R.layout.layout_spinner_item, parent, false).apply {
                viewHolder = ViewHolder(this)
                tag = viewHolder
            }
        }else{
            viewHolder = view.tag as ViewHolder
        }

        return view!!
    }

    class ViewHolder(private val view: View){
        fun bind(data: String){
            view.findViewById<TextView>(R.id.textView_category).text = data
        }
    }

}