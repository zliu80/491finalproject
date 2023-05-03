package com.zql.travelassistant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zql.travelassistant.R
import com.zql.travelassistant.bean.Reviews
import com.zql.travelassistant.interfaces.OnItemClickListener
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

/**
 * Adapter for RecyvlerView: AttractionDetailActivity
 */
class AttractionReviewsRecyclearViewAdapter(val context: Context?, var data:List<Reviews>):
        RecyclerView.Adapter<AttractionReviewsRecyclearViewAdapter.ViewHolder>(){

        lateinit var itemClickListener: OnItemClickListener

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val avatarView: AvatarView = view.findViewById(R.id.avatar_view)
            val name: TextView = view.findViewById(R.id.text_author_name)
            val text:TextView = view.findViewById(R.id.text)
            val relative_time_description:TextView = view.findViewById(R.id.text_relative_time_descrription)
            val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionReviewsRecyclearViewAdapter.ViewHolder {
            val view  = LayoutInflater.from(context).inflate(R.layout.item_attraction_reviews_recyclerview, parent, false)
            return ViewHolder(view)
        }

        fun setOnItemClickListener(listener: OnItemClickListener){
            itemClickListener = listener
        }


        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(vh: ViewHolder, position: Int) {
            vh.avatarView.loadImage(data.get(position).profile_photo_url)
            vh.name.text = data.get(position).author_name
            vh.text.text = data.get(position).text
            vh.relative_time_description.text = data.get(position).relative_time_description
            vh.ratingBar.rating = data.get(position).rating.toFloat()

            vh.itemView.setOnClickListener {
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(position)
                }
            }
        }

    }