package com.zql.travelassistant

import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.wordle.client.util.LocalDBHelper
import com.zql.travelassistant.adapter.AttractionReviewsRecyclearViewAdapter
import com.zql.travelassistant.bean.*
import com.zql.travelassistant.databinding.ActivityAttractionDetailBinding
import com.zql.travelassistant.fragment.BottomDialogSheetAddPlansFragment
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.util.BadResponseHandler
import com.zql.travelassistant.interfaces.OnItemClickListener
import com.zql.travelassistant.util.ErrorHandler
import com.zql.travelassistant.util.MaterialDialog
import com.zql.travelassistant.view.RecyclerViewOrientationManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AttractionDetailActivity : BaseActivityWithTitle(), View.OnClickListener {
    // Layout binding
    private lateinit var binding: ActivityAttractionDetailBinding

    // Attraction entity
    private lateinit var attraction: Attraction

    // Attraction detail entity
    private lateinit var placeDetailResult: PlaceDetailResult

    // City name passed from
    private var cityName: String = ""

    private var cityId: String = ""



    /**
     * Init all views and its events
     */
    override fun init() {
        binding = ActivityAttractionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cityName = intent.getStringExtra("city_name")!!
        cityId = intent.getStringExtra("city_id")!!

        attraction = Gson().fromJson(intent.getStringExtra("attraction"), Attraction::class.java)
        Picasso.get().load(TSApplication.getAttractionAbsoluteImageUrl(attraction))
            .into(binding.attractionImage)
        binding.textAttractionName.text = attraction.name
        binding.textAttractionDetail.text = attraction.description

        binding.btnHangout.setOnClickListener(this)
        binding.btnHotel.setOnClickListener(this)
        binding.btnRestaurant.setOnClickListener(this)
        binding.btnShopping.setOnClickListener(this)
        binding.btnAddToPlans.setOnClickListener(this)
        binding.expandTextView.setOnExpandStateChangeListener { textView, isExpanded ->
            showCurrentOpenningHours()
        }

        loadAttraction()
    }

    private fun showCurrentOpenningHours(){
        if(placeDetailResult.current_opening_hours!=null){
            var calendar = Calendar.getInstance()
            var day = calendar.get(Calendar.DAY_OF_WEEK)
            var list = placeDetailResult.current_opening_hours.weekday_text
            if(day == 1){
                Collections.swap(list, 0, list.size - 1)
            } else {
                Collections.swap(list, 0, day - 2)
            }
            var msg = list.joinToString("\n")

            binding.expandTextView.text = msg
        }
    }

    /**
     * Update the attraction detail
     */
    private fun updateAttractionDetail(candidate: PlaceDetailResult) {
        placeDetailResult = candidate
        binding.textAddress.text = candidate.formatted_address
        if (candidate.current_opening_hours != null) {
            if (candidate.current_opening_hours.open_now) {
                binding.textOpenningHoursStatus.text = "Open"
                binding.textOpenningHoursStatus.setTextColor(Color.GREEN)
            } else {
                binding.textOpenningHoursStatus.text = "Closed"
                binding.textOpenningHoursStatus.setTextColor(Color.RED)
            }
        }

        binding.ratingBar.rating = candidate.rating

        binding.textPhoneNumber.text = candidate.formatted_phone_number

        binding.recyclerView.layoutManager = RecyclerViewOrientationManager.getLayoutManager(resources, mContext)
        if (candidate.reviews != null) {
            val adapter = AttractionReviewsRecyclearViewAdapter(mContext, candidate.reviews)
            adapter.setOnItemClickListener(recyclerViewItemClikListener)
            binding.recyclerView.adapter = adapter
        } else {
            binding.textReviews.visibility = View.INVISIBLE
        }
        showCurrentOpenningHours()
    }

    private val recyclerViewItemClikListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {

        }
    }

    /**
     * Load attraction detail
     */
    private fun loadAttractionDetail(place_id: String) {
        // search filter
        var filter =
            "formatted_address,formatted_phone_number,name,rating,geometry,current_opening_hours,reviews"
        RetrofitClient.api.placeDetail(filter, place_id, TSApplication.GOOGLE_MAPS_API_KEY)
            .enqueue(object : Callback<PlaceDetail> {
                override fun onResponse(call: Call<PlaceDetail>, response: Response<PlaceDetail>) {
                    closeLoading()
                    if (response.code() == 200) {
                        updateAttractionDetail(response.body()!!.result)
                        Log.d("Got the place detail", response.body().toString())
                    } else {
                        closeLoading()
                        // Handle 400, 403, 404 fail
                        // Sign up failed
                        BadResponseHandler.handleErrorResponse(
                            mContext, response, "Cannot load the weather."
                        )
                    }
                    println(response.body())
                }

                override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {
                    closeLoading()
                    BadResponseHandler.handleFailtureResponse(mContext)
                }

            })
    }

    /**
     * Load attraction
     */
    private fun loadAttraction() {
        var input = attraction.name + " in " + cityName
        showLoading()
        RetrofitClient.api.searchPlace("", input, "textquery", TSApplication.GOOGLE_MAPS_API_KEY)
            .enqueue(object : Callback<PlaceSearch> {
                override fun onResponse(call: Call<PlaceSearch>, response: Response<PlaceSearch>) {
                    if (response.code() == 200) {
                        loadAttractionDetail(response.body()!!.candidates[0].place_id)
                        Log.d("Got the attraction", response.body().toString())
                    } else {
                        closeLoading()
                        // Handle 400, 403, 404 fail
                        // Sign up failed
                        BadResponseHandler.handleErrorResponse(
                            mContext, response, "Cannot load the weather."
                        )
                    }
                    println(response.body())
                }

                override fun onFailure(call: Call<PlaceSearch>, t: Throwable) {
                    closeLoading()
                    BadResponseHandler.handleFailtureResponse(mContext)
                }

            })
    }

    /**
     * All button click event
     */
    override fun onClick(view: View?) {
        var query = ""
        if (view == binding.btnHangout) {
            query = "hangouts"
        } else if (view == binding.btnHotel) {
            query = "hotels"
        } else if (view == binding.btnShopping) {
            query = "shopping"
        } else if (view == binding.btnRestaurant) {
            query = "restaurants"
        } else if (view == binding.btnAddToPlans){
            val plans = Plans(0, cityId, cityName, TSApplication.userRecord!!.id)
            BottomDialogSheetAddPlansFragment(plans, attraction).show(supportFragmentManager, "")
            return
        }
        var intent = Intent(mContext, GoogleMapsActivity::class.java)
        intent.putExtra("query", query)
        intent.putExtra("attraction_name", attraction.name)
        intent.putExtra("latitude", placeDetailResult.geometry.location.lat)
        intent.putExtra("longtitude", placeDetailResult.geometry.location.lng)
        startActivity(intent)
    }


}