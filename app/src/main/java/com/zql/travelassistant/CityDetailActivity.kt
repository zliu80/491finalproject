package com.zql.travelassistant

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.youth.banner.indicator.CircleIndicator
import com.zql.travelassistant.adapter.AttractionListRecyclearViewAdapter
import com.zql.travelassistant.adapter.CityDetailBannerImageAdapter
import com.zql.travelassistant.bean.*
import com.zql.travelassistant.databinding.ActivityCityDetailBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.util.BadResponseHandler
import com.zql.travelassistant.interfaces.OnItemClickListener
import com.zql.travelassistant.util.ErrorHandler
import com.zql.travelassistant.view.RecyclerViewOrientationManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CityDetailActivity : BaseActivityWithTitle() {

    private lateinit var binding: ActivityCityDetailBinding

    private lateinit var city: City

    private lateinit var weather: Weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    /**
     * Init views
     */
    override fun init() {
        binding = ActivityCityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.text_city_detail_activity_title)

        city = Gson().fromJson(intent.getStringExtra("city_detail"), City::class.java)
        binding.textCityName.setText(city.name)
        binding.textCityDetail.setText(city.description)

        loadWeather()

        loadAttractionList()



    }

    /**
     * Update weather information
     */
    fun updateWeatherView(weather: Weather) {
        Picasso.get().load("https:" + weather.current.condition.icon).into(binding.imageViewWeather)
        binding.textTemp.setText(weather.current.temp_f.toString() + "°F")
        binding.textHumidity.setText("Humidity: " + weather.current.humidity.toString())
        binding.textCondition.setText(weather.current.condition.text)
    }

    /**
     * Load weather by city name
     */
    fun loadWeather() {
        RetrofitClient.api.realTimeWeather(city.name)
            .enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    if (response.code() == 200) {
                        weather = response.body()!!
                        updateWeatherView(weather)
                        Log.d("Got the weather", response.body().toString());
                    } else {
                        // Handle 400, 403, 404 fail
                        // Sign up failed
                        BadResponseHandler.handleErrorResponse(
                            mContext,
                            response,
                            "Cannot load the weather."
                        )
                    }
                    println(response.body())
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    BadResponseHandler.handleFailtureResponse(mContext, t.message!!)
                }
            })
    }

    /**
     * Load Attraction List
     */
    fun loadAttractionList() {
        showLoading()
        // search filter
        var filter = "(city_id='${city.id}')"
        RetrofitClient.api.attractionList(filter).enqueue(object : Callback<AttractionList> {
            override fun onResponse(
                call: Call<AttractionList>,
                response: Response<AttractionList>
            ) {
                closeLoading()
                if (response.code() == 200) {
                    var list = response.body()!!.items
                    updateAttractionList(list)
                    updateBanner(list)
                    Log.d("Got the attraction list", response.body().toString());
                } else {
                    // Handle 400, 403, 404 fail
                    // Sign up failed
                    BadResponseHandler.handleErrorResponse(mContext, response, "Cannot load the weather.")
                }
                println(response.body())
            }

            override fun onFailure(call: Call<AttractionList>, t: Throwable) {
                closeLoading()
                BadResponseHandler.handleFailtureResponse(mContext)
            }

        })
    }

    /**
     * Update Banner
     */
    fun updateBanner(list: MutableList<Attraction>) {
        val adapter = CityDetailBannerImageAdapter(list)
        binding.banner.addBannerLifecycleObserver(this).setAdapter(adapter)
            .setIndicator(CircleIndicator(this))
        adapter.setOnItemClickListener(bannerItemClickListener)
    }

    /**
     * Update Attraction List
     */
    fun updateAttractionList(list: MutableList<Attraction>) {

        binding.recyclerView.layoutManager = RecyclerViewOrientationManager.getLayoutManager(resources, mContext)

        val adapter = AttractionListRecyclearViewAdapter(mContext, list)
        adapter.setOnItemClickListener(attactionListItemClickListener)
        binding.recyclerView.adapter = adapter
    }

    /**
     * Public method: navigate to next Activity
     */
    private fun navigateToAttractionDetailActivity(attraction: Attraction) {
        val intent = Intent(mContext, AttractionDetailActivity::class.java)
        intent.putExtra("attraction", Gson().toJson(attraction))
        intent.putExtra("city_name", city.name)
        startActivity(intent)
    }

    /**
     * Attraction List item click event
     */
    private val attactionListItemClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {
            val adapter = binding.recyclerView.adapter as AttractionListRecyclearViewAdapter
            navigateToAttractionDetailActivity(adapter.data[position])
        }
    }

    /**
     * Banner Item click event
     */
    private val bannerItemClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {
            val adapter = binding.banner.adapter as CityDetailBannerImageAdapter
            navigateToAttractionDetailActivity(adapter.data[position])
        }
    }


}