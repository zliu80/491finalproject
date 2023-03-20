package com.zql.travelassistant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import com.zql.travelassistant.R
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.bean.Attraction
import com.zql.travelassistant.interfaces.OnItemClickListener

class AttractionListRecyclearViewAdapter(val context: Context?, var data:MutableList<Attraction>):
        RecyclerView.Adapter<AttractionListRecyclearViewAdapter.ViewHolder>(){

        lateinit var itemClickListener: OnItemClickListener

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.img_recycler_view_item)
            val name: TextView = view.findViewById(R.id.title)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionListRecyclearViewAdapter.ViewHolder {
            val view  = LayoutInflater.from(context).inflate(R.layout.item_city_detail_recyclerview, parent, false)
            return ViewHolder(view)
        }

        fun setOnItemClickListener(listener: OnItemClickListener){
            itemClickListener = listener
        }


        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(vh: ViewHolder, position: Int) {
            Picasso.get().load(TSApplication.getAttractionAbsoluteImageUrl(data.get(position))).into(vh.image)

            vh.name.setText(data.get(position).name)

            vh.itemView.setOnClickListener {
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(position)
                }
            }
        }

    }
