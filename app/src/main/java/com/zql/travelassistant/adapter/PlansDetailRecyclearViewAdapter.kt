package com.zql.travelassistant.adapter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mcxtzhang.swipemenulib.SwipeMenuLayout
import com.wordle.client.util.LocalDBHelper
import com.zql.travelassistant.R
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.bean.Plans
import com.zql.travelassistant.bean.PlansDetail
import com.zql.travelassistant.interfaces.OnItemClickListener
import com.zql.travelassistant.interfaces.OnPlansDetailRemoveListener
import com.zql.travelassistant.interfaces.OnPlansItemClickListener

class PlansDetailRecyclearViewAdapter(val context: Context?, var data:MutableList<PlansDetail>, var plans: Plans, var listview:RecyclerView, var onPlansDetailRemoveListener: OnPlansDetailRemoveListener):
    RecyclerView.Adapter<PlansDetailRecyclearViewAdapter.ViewHolder>(){

    // Local db util helper
    private var dbHelper: LocalDBHelper? =null
    // Local db object
    private var db:SQLiteDatabase? = null

    lateinit var itemClickListener: OnPlansItemClickListener


    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val linearLayout:LinearLayout = view.findViewById(R.id.linearlayout)
        val attractionName:TextView = view.findViewById(R.id.text_attraction_name)
        val description:TextView = view.findViewById(R.id.text_description)
        val startDate:TextView = view.findViewById(R.id.text_start_date)
        val endDate:TextView = view.findViewById(R.id.text_end_date)
        val deleteButton:Button = view.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansDetailRecyclearViewAdapter.ViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.item_plans_detail, parent, false)
        dbHelper = LocalDBHelper(context)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {

        val plansDetail = data.get(position)

        vh.linearLayout.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(plans, plansDetail, position)
            }
        }

        vh.attractionName.text = plansDetail.attraction_name
        vh.description.text = plansDetail.description

        vh.startDate.text = plansDetail.start_date
        vh.endDate.text = plansDetail.end_date

        vh.deleteButton.setOnClickListener {
            try {
                if(removeFromDB(plansDetail)) {
//                        Log.d(TAG, "Delete item succeesfully!")
                    data.remove(plansDetail)
                    if(data.size == 0){
                        onPlansDetailRemoveListener.onClear(plans)
                    }

                    listview.adapter = PlansDetailRecyclearViewAdapter(context, data,plans, listview, onPlansDetailRemoveListener)
                } else{
//                        Log.d(TAG, "Delete item failed!")
                }
            } catch (e:Exception){
//                    Log.d(TAG, "Delete item failed!")
                Toast.makeText(context, "Failed to remove item", Toast.LENGTH_SHORT).show()
            }
        }


    }

    /**
     * Delete a favorite item from local database
     */
    fun removeFromDB(plansDetail: PlansDetail):Boolean{
        return getDB()?.delete("plansdetail", "id=?", arrayOf(plansDetail.id.toString()))!! > 0
    }



    /**
     * Get the local database object
     */
    fun getDB(): SQLiteDatabase?{
        if (db==null){
            try {
                db = dbHelper?.writableDatabase
            } catch (e: Exception){
                db = dbHelper?.readableDatabase
            }
        }
        return db
    }

    fun setOnItemClickListener(listener: OnPlansItemClickListener) {
        itemClickListener = listener
    }

}