package com.zql.travelassistant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zql.travelassistant.R
import com.zql.travelassistant.interfaces.OnItemClickListener

class SettingsRecyclearViewAdapter(val context: Context?, var data:MutableList<String>):
    RecyclerView.Adapter<SettingsRecyclearViewAdapter.ViewHolder>(){

    lateinit var itemClickListener: OnItemClickListener


    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
            val tv_recycler_view_item: TextView = view.findViewById(R.id.tv_recycler_view_item)
//
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsRecyclearViewAdapter.ViewHolder {
            val view  = LayoutInflater.from(context).inflate(R.layout.item_settings_recyclerview, parent, false)
            return ViewHolder(view)
        }


        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(vh: ViewHolder, position: Int) {
            vh.tv_recycler_view_item.text = data.get(position)
            vh.itemView.setOnClickListener {
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(position)
                }
            }
        }

        fun setOnItemClickListener(listener: OnItemClickListener){
            itemClickListener = listener
        }

    }