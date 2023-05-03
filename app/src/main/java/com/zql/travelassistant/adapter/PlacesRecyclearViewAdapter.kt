package com.zql.travelassistant.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zql.travelassistant.R
import com.zql.travelassistant.bean.Results
import com.zql.travelassistant.fragment.BottomDialogSheetFragment
import com.zql.travelassistant.interfaces.OnItemClickListener

/**
 * Adapter for RecyvlerView: GoogleMapsActivity
 */
class PlacesRecyclearViewAdapter(val supportFragmentManager:FragmentManager, val context: Activity?, var data: List<Results>):
        RecyclerView.Adapter<PlacesRecyclearViewAdapter.ViewHolder>(){

        lateinit var itemClickListener: OnItemClickListener

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.text_place_name)
//            val address:TextView = view.findViewById(R.id.text_address)
            val icon: ImageView = view.findViewById(R.id.icon)
            val ratingBar:RatingBar = view.findViewById(R.id.rating_bar)
            val locateButton: Button = view.findViewById(R.id.btn_locate)
            val openningStatus:TextView = view.findViewById(R.id.text_openning_hours_status)
            val detailButton:Button = view.findViewById(R.id.btn_detail)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesRecyclearViewAdapter.ViewHolder {
            val view  = LayoutInflater.from(context).inflate(R.layout.item_maps_place_recyclerview, parent, false)
            return ViewHolder(view)
        }

        fun setOnItemClickListener(listener: OnItemClickListener){
            itemClickListener = listener
        }


        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(vh: ViewHolder, position: Int) {
            var item = data.get(position)
            vh.icon.setBackgroundColor(Color.parseColor(item.icon_background_color))
            Picasso.get().load(item.icon).into(vh.icon)
            vh.name.text = item.name
            vh.ratingBar.rating = item.rating
//            vh.address.setText(item.formatted_address)
            vh.itemView.setOnClickListener {
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(position)
                }
            }
            vh.locateButton.setOnClickListener {
                val gmmIntentUri =
                    Uri.parse("google.navigation:q=" + item.geometry.location.lat+"," + item.geometry.location.lng)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                context?.startActivity(mapIntent)
            }

            vh.detailButton.setOnClickListener {
                BottomDialogSheetFragment(item).show(supportFragmentManager,"BottomDialogSheetFragment")
            }
            if(item.opening_hours!=null) {
                if (item.opening_hours.open_now) {
                    vh.openningStatus.text = "Open"
                    vh.openningStatus.setTextColor(Color.GREEN)
                } else {
                    vh.openningStatus.text = "Closed"
                    vh.openningStatus.setTextColor(Color.RED)
                }
            }

        }

    }