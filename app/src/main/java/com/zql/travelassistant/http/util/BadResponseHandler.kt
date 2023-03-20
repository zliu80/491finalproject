package com.zql.travelassistant.http.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zql.travelassistant.http.model.UserBadResponse
import okhttp3.ResponseBody
import retrofit2.Response

class BadResponseHandler {
    companion object{

        fun handleErrorResponse(context: Context?, response:Response<*>){
            if(response.errorBody()!=null) {
                val type = object : TypeToken<UserBadResponse>() {}.type
                val errorResponse: UserBadResponse? =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                Toast.makeText(context, errorResponse?.message, Toast.LENGTH_SHORT).show()
//                Log.e("Error code " + errorResponse?.code, response.errorBody().toString())
            } else {
//                Log.e("Error code " + response.code(), response.errorBody().toString())
            }
        }

        fun handleErrorResponse(context: Context?, response:Response<*>, specialMsg:String){
            if(response.errorBody()!=null) {
                val type = object : TypeToken<UserBadResponse>() {}.type
                val errorResponse: UserBadResponse? =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                Toast.makeText(context, errorResponse?.message, Toast.LENGTH_SHORT).show()
//                Log.e("Error code " + errorResponse?.code, response.errorBody().toString())
            } else {
                Toast.makeText(context, specialMsg, Toast.LENGTH_SHORT).show()
//                Log.e("Error code " + response.code(), response.errorBody().toString())
            }
        }

        var FAILURE_MSG = "Cannot connect to the server"
        var FAILURE_TAG = "OnFailure"

        fun handleFailtureResponse(context: Context?){
            Log.e(FAILURE_TAG, FAILURE_MSG)
            Toast.makeText(context, FAILURE_MSG, Toast.LENGTH_SHORT).show()
        }
    }
}