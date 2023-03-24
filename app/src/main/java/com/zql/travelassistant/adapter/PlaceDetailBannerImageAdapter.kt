package com.zql.travelassistant.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.bean.Attraction
import com.zql.travelassistant.interfaces.OnItemClickListener

/**
 * Adapter for RecyvlerView: CityDetailActivity
 */
class PlaceDetailBannerImageAdapter(var data: List<String>) :
    BannerAdapter<String?, PlaceDetailBannerImageAdapter.BannerViewHolder?>(data) {

    lateinit var itemClickListener: OnItemClickListener

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: String?,
        position: Int,
        size: Int
    ) {
        Picasso.get().load(data)
            .into(holder?.imageView)

        holder?.imageView?.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position)
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)

}