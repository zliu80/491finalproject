package com.zql.travelassistant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zql.travelassistant.R
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.bean.City
import com.zql.travelassistant.interfaces.OnItemClickListener

class HomeRecyclearViewAdapter(val context: Context?, var data:MutableList<City>):
        RecyclerView.Adapter<HomeRecyclearViewAdapter.ViewHolder>(){

        lateinit var itemClickListener: OnItemClickListener

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val cityImage: ImageView = view.findViewById(R.id.img_recycler_view_item)
            val cityTitle: TextView = view.findViewById(R.id.title)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclearViewAdapter.ViewHolder {
            val view  = LayoutInflater.from(context).inflate(R.layout.item_home_recyclerview, parent, false)
            return ViewHolder(view)
        }

        fun setOnItemClickListener(listener: OnItemClickListener){
            itemClickListener = listener
        }


        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(vh: ViewHolder, position: Int) {
            Picasso.get().load(TSApplication.getCityAbsoluteImageUrl(data.get(position))).into(vh.cityImage)

            vh.cityTitle.text = data.get(position).name

            vh.itemView.setOnClickListener {
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(position)
                }
            }
        }

    }