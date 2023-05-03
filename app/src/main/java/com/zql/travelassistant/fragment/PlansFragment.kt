package com.zql.travelassistant.fragment

import android.content.res.Configuration
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wordle.client.util.LocalDBHelper
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.adapter.PlansRecyclearViewAdapter
import com.zql.travelassistant.bean.Plans
import com.zql.travelassistant.databinding.FragmentPlansBinding
import com.zql.travelassistant.interfaces.OnPlansModifyListener

class PlansFragment : Fragment(), OnPlansModifyListener {

    companion object {
        fun newInstance() = PlansFragment()
    }

    private lateinit var viewModel: PlansViewModel

    private lateinit var binding:FragmentPlansBinding

    // Local db util helper
    private var dbHelper: LocalDBHelper? =null
    // Local db object
    private var db:SQLiteDatabase? = null

            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
                binding = FragmentPlansBinding.inflate(layoutInflater)
                return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlansViewModel::class.java)
        dbHelper = LocalDBHelper(requireContext())


       }

    override fun onResume() {
        super.onResume()
        initViews()
    }

    private fun initViews(){
        var layoutManager: RecyclerView.LayoutManager?= null
        // Dynamically choose layout based on the orientation of device
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = GridLayoutManager(context,3)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
        } else  if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = LinearLayoutManager(context)
        }

        binding.listview.layoutManager = layoutManager
        binding.listview.adapter = PlansRecyclearViewAdapter(context, fragmentManager, loadAllPlansByUserId(), binding.listview, this)

    }

    private fun loadAllPlansByUserId():MutableList<Plans>{
        var plans:MutableList<Plans> = mutableListOf()
        // find all the languages like "select * from languages" statement
        var cursor = getDB()?.query("plans",null,"user_id=?", arrayOf(TSApplication.userRecord?.id),null,null,null)
        if(cursor!!.moveToFirst()){
            do{
                var p = Plans(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3))
                plans.add(p)
            } while(cursor.moveToNext())
        }
        return plans
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

    override fun onModified() {
        initViews()
    }
}