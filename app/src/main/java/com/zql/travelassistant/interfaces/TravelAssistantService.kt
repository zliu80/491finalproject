package com.zql.travelassistant.interfaces

import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.bean.*
import com.zql.travelassistant.http.model.HttpReturnData
import com.zql.travelassistant.http.model.SignUpData
import com.zql.travelassistant.http.model.UpdateUserAvatar
import com.zql.travelassistant.http.model.UpdateUserData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface TravelAssistantService {

    /****************************************************
     ************************* User **********************
     ***************************************************/
    @FormUrlEncoded
    @POST("users/auth-with-password")
    fun logIn(@Field("identity") identity: String, @Field("password") password: String): Call<Auth>

    @POST("users/records")
    fun signUp(@Body dataModel: SignUpData?): Call<User>

    @Multipart
    @PATCH("users/records/{id}")
    fun updateUserAvatar(@Path("id") id: String, @Part photo: MultipartBody.Part): Call<User>

    @PATCH("users/records/{id}")
    fun updateUserProfile(@Path("id") id: String, @Body data: UpdateUserData): Call<User>

    /****************************************************
     ************************* City *********************
     ***************************************************/
    @GET("city/records")
    fun cities(): Call<CityList>

    /****************************************************
     ********************** Attraction ******************
     ***************************************************/
    @GET("attraction/records")
    fun attractionList(@Query("filter") filter: String): Call<AttractionList>

    /****************************************************
     ****************** Weather Service *****************
     ***************************************************/
    @GET(TSApplication.WEATHER_API_REALTIME_URL)
    @Headers(
        "X-RapidAPI-Key:6cc1aa3ed3msh2cd79da32284ec1p1c8979jsna8f06a559504",
        "X-RapidAPI-Host:weatherapi-com.p.rapidapi.com"
    )
    fun realTimeWeather(@Query("q") q: String): Call<Weather>

    /****************************************************
     ************** Google Map Service ******************
     ***************************************************/
    @GET("https://maps.googleapis.com/maps/api/place/findplacefromtext/json")
    fun searchPlace(
        @Query("fields") field: String,
        @Query("input") input: String,
        @Query("inputtype") inputtype: String,
        @Query("key") key: String
    ): Call<PlaceSearch>

    @GET("https://maps.googleapis.com/maps/api/place/details/json")
    fun placeDetail(
        @Query("fields") field: String,
        @Query("place_id") place_id: String,
        @Query("key") key: String
    ): Call<PlaceDetail>

    @GET("https://maps.googleapis.com/maps/api/place/textsearch/json")
    fun searchPlaces(
        @Query("location") location: String,
        @Query("query") name: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): Call<Place>


}