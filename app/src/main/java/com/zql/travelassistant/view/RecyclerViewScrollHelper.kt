package com.zql.travelassistant.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewScrollHelper {
    fun scrollToPosition(recyclerView: RecyclerView, position: Int) {
        val manager1 = recyclerView.layoutManager
        if (manager1 is LinearLayoutManager) {
            val mScroller = TopSmoothScroller(recyclerView.context)
            mScroller.targetPosition = position
            manager1.startSmoothScroll(mScroller)
        }
    }

    class TopSmoothScroller internal constructor(context: Context?) :
        LinearSmoothScroller(context) {

        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
}