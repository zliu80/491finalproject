package com.zql.travelassistant.http

import com.zql.travelassistant.bean.Auth
import com.zql.travelassistant.bean.UserRecord
import com.zql.travelassistant.http.model.SignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface TravelAssistantService {

    @FormUrlEncoded
    @POST("users/auth-with-password")
    fun LogIn(@Field("identity") identity:String, @Field("password") password:String):Call<Auth>

    @POST("users/records")
    fun SignUp(@Body dataModel:SignUpData?):Call<UserRecord>
}