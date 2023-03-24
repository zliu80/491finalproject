package com.zql.travelassistant.view

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewOrientationManager {

    companion object{
        fun getLayoutManager(resources: Resources, mContext:Context): RecyclerView.LayoutManager? {
            var layoutManager: RecyclerView.LayoutManager? = null
            // Dynamically choose layout based on the orientation of device
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = GridLayoutManager(mContext, 3)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
            } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = LinearLayoutManager(mContext)
            }
            return layoutManager

        }
    }


}