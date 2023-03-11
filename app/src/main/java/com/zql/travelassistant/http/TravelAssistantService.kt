package com.zql.travelassistant.http

import com.zql.travelassistant.bean.Auth
import com.zql.travelassistant.bean.CityList
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.http.model.SignUpData
import com.zql.travelassistant.http.model.UpdateUserAvatar
import com.zql.travelassistant.http.model.UpdateUserData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface TravelAssistantService {

    @FormUrlEncoded
    @POST("users/auth-with-password")
    fun logIn(@Field("identity") identity:String, @Field("password") password:String):Call<Auth>

    @POST("users/records")
    fun signUp(@Body dataModel:SignUpData?):Call<User>

    @Multipart
    @PATCH("users/records/{id}")
    fun updateUserAvatar(@Path("id") id:String, @Part photo:MultipartBody.Part):Call<User>

    @PATCH("users/records/{id}")
    fun updateUserProfile(@Path("id") id:String, @Body data: UpdateUserData):Call<User>

    @GET("city/records")
    fun cities():Call<CityList>

}