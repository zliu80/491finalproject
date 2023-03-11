package com.zql.travelassistant.http

import com.zql.travelassistant.TSApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class defines the Retrofit Client and how its setting
 */
object RetrofitClient {

    // OKhttp logging setting
    val loggingInterceptor = HttpLoggingInterceptor(HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY)

    // OKhttp client register with logging
    val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    // Retrofit client with OKhttp
    val retrofit = Retrofit.Builder().baseUrl(TSApplication.TRAVEL_ASSISTANT_URL).addConverterFactory(
        GsonConverterFactory.create()).client(
        okHttpClient).build()

    // Travel Assistant service created by retrofit client
    val api = retrofit.create(TravelAssistantService::class.java)
}