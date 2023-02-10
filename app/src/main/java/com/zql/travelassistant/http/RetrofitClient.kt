package com.zql.travelassistant.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class defines the Retrofit Client and how its setting
 */
object RetrofitClient {
    // Local Pocketbase Restful API
    const val TRAVEL_ASSISTANT_BASE_URL = "http://10.0.2.2:8090"

    const val TRAVEL_ASSISTANT_URL = """$TRAVEL_ASSISTANT_BASE_URL/api/collections/"""

    // OKhttp logging setting
    val loggingInterceptor = HttpLoggingInterceptor(HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY)

    // OKhttp client register with logging
    val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    // Retrofit client with ok http
    val retrofit = Retrofit.Builder().baseUrl(TRAVEL_ASSISTANT_URL).addConverterFactory(
        GsonConverterFactory.create()).client(
        okHttpClient).build()

    // Translate service created by retrofit client
    val api = retrofit.create(TravelAssistantService::class.java)
}