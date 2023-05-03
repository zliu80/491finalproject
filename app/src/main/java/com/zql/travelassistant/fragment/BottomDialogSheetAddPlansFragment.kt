package com.zql.travelassistant.fragment

import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.wordle.client.util.LocalDBHelper
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.bean.*
import com.zql.travelassistant.databinding.FragmentBottomSheetAddPlansBinding
import java.text.SimpleDateFormat
import java.util.Calendar

open class BottomDialogSheetAddPlansFragment(plans:Plans, attraction: Attraction): SuperBottomSheetFragment(), View.OnClickListener {

    lateinit var binding:FragmentBottomSheetAddPlansBinding

    var plans: Plans

    var attraction:Attraction

    // Local db util helper
    private var dbHelper: LocalDBHelper? =null
    // Local db object
    private var db:SQLiteDatabase? = null

    init{
        this.plans = plans
        this.attraction = attraction
    }

    private fun initViews(){
        binding.textStartDate.setOnClickListener(this)
        binding.textEndDate.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.btnConfirm.setOnClickListener(this)

        binding.textDestinationCity.text = plans.city_name
        binding.textDestination.text = attraction.name
    }

    override fun onClick(view: View?) {
        if(view == binding.textStartDate){
            val calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val simpleDateFormat:SimpleDateFormat = SimpleDateFormat()
                simpleDateFormat.applyPattern("yyyy/MM/dd")

                binding.textStartDate.text = simpleDateFormat.format(calendar.time)

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        } else if (view == binding.textEndDate){
            val calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val simpleDateFormat:SimpleDateFormat = SimpleDateFormat()
                simpleDateFormat.applyPattern("yyyy/MM/dd")

                binding.textEndDate.text = simpleDateFormat.format(calendar.time)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        } else if (view == binding.btnCancel){
            dismiss()
        } else if (view == binding.btnConfirm){
            confirm()
        }
    }

    private fun confirm(){
        var p = isPlanExisted()
        var plans_id = 0
        if(p == null){
            plans_id = addToPlans()!!.toInt()
        } else {
            plans_id = p.id
        }

        var result = addToPlansDetail(plans_id)
        if(result== -1L){
            com.zql.travelassistant.util.MaterialDialog.show(requireActivity(), "failed to add to plans", "error")
        } else {
            Toast.makeText(requireContext(), "Added to your plans.", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // the layout for the dialog
        binding = FragmentBottomSheetAddPlansBinding.inflate(layoutInflater)
        initViews()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = LocalDBHelper(context)
    }


    private fun isPlanExisted():Plans?{
        var cursor = getDB()?.query("plans", null, "city_id=? and user_id=?", arrayOf(plans.city_id, plans.user_id), null, null, null)
        if(cursor!!.moveToNext()){
            do{
                var p = Plans(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3))
                return p
            } while (cursor.moveToNext())
        }
        return null
    }

    private fun addToPlans():Long?{
        try {

            var content = ContentValues().apply {
                    put("city_id", plans.city_id)
                    put("city_name", plans.city_name)
                    put("user_id", TSApplication.userRecord!!.id)
            }
            var plans_id = getDB()?.insert("plans", null, content)
            return plans_id
        } catch (e:java.lang.Exception){
            com.zql.travelassistant.util.MaterialDialog.show(requireActivity(), "failed to add to plans", "error")
        }
        return -1L
    }

    private fun addToPlansDetail(plans_id:Int):Long?{
        try {
            var content = ContentValues().apply {
                put("plans_id", plans_id)
                put("attraction_id", attraction.id)
                put("attraction_name", attraction.name)
                put("start_date", binding.textStartDate.text.toString())
                put("end_date", binding.textEndDate.text.toString())
                put("description", binding.textDescription.editText?.text.toString())
            }

            var result = getDB()?.insert("plansdetail", null, content)
            return result
        } catch (e:java.lang.Exception){
            com.zql.travelassistant.util.MaterialDialog.show(requireActivity(), "failed to add to plans detail", "error")

        }
        return -1L
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


    override fun getPeekHeight(): Int {

        super.getPeekHeight()

        with(resources.displayMetrics) {
            return heightPixels - 300
        }

    }

}