package com.zql.travelassistant.adapter

import android.content.Context
import android.content.res.Configuration
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wordle.client.util.LocalDBHelper
import com.zql.travelassistant.R
import com.zql.travelassistant.bean.Plans
import com.zql.travelassistant.bean.PlansDetail
import com.zql.travelassistant.fragment.BottomDialogSheetModifyPlansFragment
import com.zql.travelassistant.interfaces.OnPlansDetailRemoveListener
import com.zql.travelassistant.interfaces.OnPlansItemClickListener
import com.zql.travelassistant.interfaces.OnPlansModifyListener

class PlansRecyclearViewAdapter(val context: Context?, val supportFragmentManager: FragmentManager?, var data:MutableList<Plans>, var listview:RecyclerView, val onPlansModifyListener: OnPlansModifyListener):
    RecyclerView.Adapter<PlansRecyclearViewAdapter.ViewHolder>(), OnPlansDetailRemoveListener, OnPlansItemClickListener{

    // Local db util helper
    private var dbHelper: LocalDBHelper? =null
    // Local db object
    private var db:SQLiteDatabase? = null

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val cityName:TextView = view.findViewById(R.id.text_city_name)
        val listview:RecyclerView = view.findViewById(R.id.listview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansRecyclearViewAdapter.ViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.item_plans, parent, false)
        dbHelper = LocalDBHelper(context)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val plan = data.get(position)
        vh.cityName.text = plan.city_name

        var layoutManager: RecyclerView.LayoutManager?= null
        // Dynamically choose layout based on the orientation of device
        if(context!!.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = GridLayoutManager(context,3)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
        } else  if(context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = LinearLayoutManager(context)
        }

        vh.listview.layoutManager = layoutManager
        val adapter = PlansDetailRecyclearViewAdapter(context, loadAllPlansByUserId(plan.id), plan, vh.listview, this)
        adapter.setOnItemClickListener(this)
        vh.listview.adapter = adapter
    }

    override fun onItemClick(plans: Plans, plansDetail: PlansDetail, position: Int) {
        BottomDialogSheetModifyPlansFragment(plans, plansDetail, plansDetail.attraction_name, onPlansModifyListener).show(supportFragmentManager!!, "")
    }

    private fun loadAllPlansByUserId(plansId:Int):MutableList<PlansDetail>{
        var plansDetail:MutableList<PlansDetail> = mutableListOf()
        // find all the languages like "select * from languages" statement
        var cursor = getDB()?.query("plansdetail",null,"plans_id=?", arrayOf(plansId.toString()),null,null,null)
        if(cursor!!.moveToFirst()){
            do{
                var p = PlansDetail(cursor.getInt(0), cursor.getInt(2), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6))
                plansDetail.add(p)
            } while(cursor.moveToNext())
        }
        return plansDetail
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

    override fun onClear(plans: Plans) {
        removePlansFromDB(plans.city_id, plans.user_id)
        data.remove(plans)
        listview.adapter = PlansRecyclearViewAdapter(context, supportFragmentManager, data, listview, onPlansModifyListener)
    }

    fun removePlansFromDB(cityId:String, userId:String):Boolean{
        return getDB()?.delete("plans", "user_id=? and city_id=?", arrayOf(userId, cityId))!! > 0
    }

}