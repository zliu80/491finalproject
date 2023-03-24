package com.zql.travelassistant.util

import android.R
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import dev.shreyaspatil.MaterialDialog.MaterialDialog


class ErrorHandler {

    companion object{
        fun showErrorMsg(activity: Activity){
            var msg = "Unexpected fatal error! Contact the author"

            val mDialog = MaterialDialog.Builder(activity)
                .setTitle("Fatal Error")
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