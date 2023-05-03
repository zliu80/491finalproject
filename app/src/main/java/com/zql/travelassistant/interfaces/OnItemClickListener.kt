package com.zql.travelassistant.interfaces

import com.zql.travelassistant.bean.Plans
import com.zql.travelassistant.bean.PlansDetail

/**
 * Item click event for RecyclerView
 */
interface OnItemClickListener{
        fun onItemClick(position: Int)
}

interface OnPlansItemClickListener{

        fun onItemClick(plans: Plans, plansDetail: PlansDetail, position: Int)

}