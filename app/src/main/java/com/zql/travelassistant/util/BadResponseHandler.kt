package com.zql.travelassistant.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zql.travelassistant.http.model.UserBadResponse
import retrofit2.Response

class BadResponseHandler {

    companion object{

        val FAILURE_MSG = "Cannot connect to the server"
        val FAILURE_TAG = "OnFailure"

        fun handleErrorResponse(context: Context?, response:Response<*>, specialMsg:String){
            if(response.errorBody()!=null) {
                val type = object : TypeToken<UserBadResponse>() {}.type
                val errorResponse: UserBadResponse? =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                MaterialDialog.show(context as Activity, errorResponse!!.message, "Error")
            } else {
                MaterialDialog.show(context as Activity, specialMsg, "Error")
            }
        }

        fun handleFailtureResponse(context: Context?){
            Log.e(FAILURE_TAG, FAILURE_MSG)
            MaterialDialog.show(context as Activity, FAILURE_MSG, "Error")
        }

        fun handleFailtureResponse(context: Context?, detailError:String){
            Log.e(FAILURE_TAG, FAILURE_MSG)
            MaterialDialog.show(context as Activity, detailError, "Error")
        }
    }
}