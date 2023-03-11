package com.zql.travelassistant

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zql.travelassistant.bean.User

class TSApplication :Application() {

    companion object{
        lateinit var userRecord: User

        // Local Pocketbase Restful API
        const val SERVER_ADDRESS = "http://10.0.2.2:8090"

        const val TRAVEL_ASSISTANT_URL = """$SERVER_ADDRESS/api/collections/"""

        const val USER_FILE_PATH = """$SERVER_ADDRESS/api/files/_pb_users_auth_/"""

        const val CITY_FILE_PATH = """$SERVER_ADDRESS/api/files/"""

        fun getAvatarHttpAddress(): String {
            return USER_FILE_PATH + userRecord?.id + "/" + userRecord?.avatar
        }
    }



    override fun onCreate() {
        super.onCreate()
    }
}