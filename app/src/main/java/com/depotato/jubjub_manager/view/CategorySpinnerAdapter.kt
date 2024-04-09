package com.depotato.jubjub_manager.view

import android.content.Context
import android.util.Log
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
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_spinner_top, parent, false)

        setCategoryText(v, dataList[position])

        return v
    }

    fun setCategoryText(view: View, category: String){
        view.findViewById<TextView>(R.id.textView_category).text = category
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder : ViewHolder
        val v : View

        if(convertView == null){
            v = inflater.inflate(R.layout.layout_spinner_item, parent, false).apply {
                viewHolder = ViewHolder(this)
                tag = viewHolder
            }
        }else{
            v = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.bind(dataList[position])

        return v
    }


    class ViewHolder(private val view: View){
        fun bind(data: String){
            view.findViewById<TextView>(R.id.textView_category).text = data
        }
    }

}