package com.zql.travelassistant

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import com.zql.travelassistant.bean.Attraction
import com.zql.travelassistant.bean.City
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.util.LocalManageUtils
import io.multimoon.colorful.CustomThemeColor
import io.multimoon.colorful.Defaults
import io.multimoon.colorful.ThemeColor
import io.multimoon.colorful.initColorful

class TSApplication :Application() {

    companion object{
         var userRecord: User? = null

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
        LocalManageUtils.setAppLanguage(this)
        var appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA   )
        GOOGLE_MAPS_API_KEY = appInfo.metaData.getString("com.google.android.geo.API_KEY")!!
        var myCustomColor1 = CustomThemeColor(
            context = this,
            R.style.Theme_TravelAssistant,
            R.color.black,
            io.multimoon.colorful.R.color.md_green_200, // <= use the color you defined in my_custom_primary_color
            io.multimoon.colorful.R.color.md_red_500 // <= use the color you defined in my_custom_primary_dark_color
        )
// used as accent color, dark color is irrelevant...
        var myCustomColor2 = CustomThemeColor(
            this,
            R.style.Theme_TravelAssistant_Night,
            R.color.black,
            io.multimoon.colorful.R.color.md_yellow_700, // <= use the color you defined in my_custom_accent_color
            io.multimoon.colorful.R.color.md_yellow_700 // <= use the color you defined in my_custom_accent_color
        )

//        val defaults:Defaults = Defaults(
//            primaryColor = ThemeColor.GREEN,
//            accentColor = ThemeColor.BLUE,
//            useDarkTheme = false,
//            translucent = false)
        val defaults:Defaults = Defaults(
        primaryColor = myCustomColor1,
        accentColor = myCustomColor2,
        useDarkTheme = false,
        translucent = false,
        customTheme = 0
        )
        initColorful(this, defaults)
    }

    override fun attachBaseContext(base: Context?) {
        LocalManageUtils.setSystemCurrentLanguage(base)
        super.attachBaseContext(LocalManageUtils.setLocale(base!!))

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocalManageUtils.onConfigurationChanged(applicationContext)
    }
}