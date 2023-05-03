package com.zql.travelassistant.interfaces

import com.zql.travelassistant.bean.Plans

/**
 * Item click event for RecyclerView
 */
interface OnPlansDetailRemoveListener{
        fun onClear(plans: Plans)
}

interface OnPlansModifyListener{
        fun onModified()
}