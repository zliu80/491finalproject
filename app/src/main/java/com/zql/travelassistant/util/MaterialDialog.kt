package com.zql.travelassistant.util

import android.R
import android.app.Activity
import dev.shreyaspatil.MaterialDialog.MaterialDialog

class MaterialDialog {

    companion object{

        fun show(activity: Activity, msg:String, title:String){

            val mDialog = MaterialDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(
                    "OK", R.drawable.ic_dialog_info
                ) { dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                .build()
            mDialog.show()
        }
    }
}