package com.zql.travelassistant

import android.app.Application
import android.content.pm.PackageManager
import androidx.appcompat.widget.ThemeUtils
import androidx.lifecycle.MutableLiveData
import com.zql.travelassistant.bean.Attraction
import com.zql.travelassistant.bean.City
import com.zql.travelassistant.bean.User

class TSApplication :Application() {

    companion object{
        lateinit var userRecord: User

        // Local Pocketbase Restful API
        const val SERVER_ADDRESS = "http://10.0.2.2:8090"

        var GOOGLE_MAPS_API_KEY = ""

        const val TRAVEL_ASSISTANT_URL = """$SERVER_ADDRESS/api/collections/"""

        const val USER_FILE_PATH = """$SERVER_ADDRESS/api/files/_pb_users_auth_/"""

        const val CITY_FILE_PATH = """$SERVER_ADDRESS/api/files/"""

        const val WEATHER_API_REALTIME_URL = "https://weatherapi-com.p.rapidapi.com/current.json"

        fun getAvatarHttpAddress(): String {
            return USER_FILE_PATH + userRecord?.id + "/" + userRecord?.avatar
        }

        fun getCityAbsoluteImageUrl(city: City):String{
            return "$CITY_FILE_PATH${city.collectionId}/${city.id}/${city.city_image}"
        }

        fun getAttractionAbsoluteImageUrl(attraction: Attraction):String{
            return "$CITY_FILE_PATH${attraction.collectionId}/${attraction.id}/${attraction.attraction_image}"
        }
    }



    override fun onCreate() {
        super.onCreate()
        var appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA   )
        GOOGLE_MAPS_API_KEY = appInfo.metaData.getString("com.google.android.geo.API_KEY")!!
    }
}