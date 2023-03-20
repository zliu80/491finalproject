package com.zql.travelassistant

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zql.travelassistant.adapter.AttractionReviewsRecyclearViewAdapter
import com.zql.travelassistant.bean.*
import com.zql.travelassistant.databinding.ActivityAttractionDetailBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.http.util.BadResponseHandler
import com.zql.travelassistant.interfaces.OnItemClickListener
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class AttractionDetailActivity : AppCompatActivity(), View.OnClickListener {
    // Layout binding
    private lateinit var binding:ActivityAttractionDetailBinding
    // Context
    private val mContext = this
    // Attraction entity
    private lateinit var attraction:Attraction
    // Attraction detail entity
    private lateinit var placeDetailResult: PlaceDetailResult
    // City name passed from
    private var cityName:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttractionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cityName = intent.getStringExtra("city_name")!!

        initViews()

        loadAttraction()
    }

    /**
     * Init all views and its events
     */
    private fun initViews(){

        attraction = Gson().fromJson(intent.getStringExtra("attraction"), Attraction::class.java)
        Picasso.get().load(TSApplication.getAttractionAbsoluteImageUrl(attraction)).into(binding.attractionImage)

        binding.textAttractionName.setText(attraction.name)
        binding.textAttractionDetail.setText(attraction.description)

        binding.btnHangout.setOnClickListener(this)
        binding.btnHotel.setOnClickListener(this)
        binding.btnRestaurant.setOnClickListener(this)
        binding.btnShopping.setOnClickListener(this)
        binding.btnOpenningHours.setOnClickListener{
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Openning Hours").setMessage(placeDetailResult.current_opening_hours.weekday_text.joinToString("\n"))
            alertDialog.show()
        }
    }

    /**
     * Update the attraction detail
     */
    private fun updateAttractionDetail(candidate: PlaceDetailResult){
        placeDetailResult = candidate
        binding.textAddress.setText(candidate.formatted_address)
        if(candidate.current_opening_hours.open_now){
            binding.textOpenningHoursStatus.setText("Open")
            binding.textOpenningHoursStatus.setTextColor(Color.GREEN)
        } else {
            binding.textOpenningHoursStatus.setText("Closed")
            binding.textOpenningHoursStatus.setTextColor(Color.RED)
        }
        binding.ratingBar.rating = candidate.rating.toFloat()

        binding.textContactNumber.setText(candidate.formatted_phone_number)

        var layoutManager:RecyclerView.LayoutManager?= null
        // Dynamically choose layout based on the orientation of device
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = GridLayoutManager(mContext,3)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
        } else  if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = LinearLayoutManager(mContext)
        }
        binding.recyclerView.layoutManager = layoutManager
        val adapter = AttractionReviewsRecyclearViewAdapter(mContext, candidate.reviews)
        adapter.setOnItemClickListener(recyclerViewItemClikListener)
        binding.recyclerView.adapter = adapter
    }

    private val recyclerViewItemClikListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {

        }
    }

    /**
     * Load attraction detail
     */
    private fun loadAttractionDetail(place_id:String){
        // search filter
        var filter="formatted_address,formatted_phone_number,name,rating,geometry,current_opening_hours,reviews"
        RetrofitClient.api.placeDetail(filter, place_id, TSApplication.GOOGLE_MAPS_API_KEY)
            .enqueue(object:Callback<PlaceDetail>{
                override fun onResponse(call: Call<PlaceDetail>, response: Response<PlaceDetail>) {
                    if (response.code() == 200 ) {
                        updateAttractionDetail(response.body()!!.result)
                        Log.d("Got the place detail", response.body().toString());
                    } else {
                        // Handle 400, 403, 404 fail
                        // Sign up failed
                        BadResponseHandler.handleErrorResponse(mContext, response, "Cannot load the weather.")
                    }
                    println(response.body())
                }

                override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {
                    println("")

                }

            })
    }

    /**
     * Load attraction
     */
    private fun loadAttraction(){
        var input = attraction.name + " in " + cityName
        RetrofitClient.api.searchPlace("", input, "textquery", TSApplication.GOOGLE_MAPS_API_KEY)
            .enqueue(object :Callback<PlaceSearch>{
                override fun onResponse(call: Call<PlaceSearch>, response: Response<PlaceSearch>) {
                    if (response.code() == 200 ) {
                        loadAttractionDetail(response.body()!!.candidates[0].place_id)
                        Log.d("Got the attraction", response.body().toString());
                    } else {
                        // Handle 400, 403, 404 fail
                        // Sign up failed
                        BadResponseHandler.handleErrorResponse(mContext, response, "Cannot load the weather.")
                    }
                    println(response.body())
                }

                override fun onFailure(call: Call<PlaceSearch>, t: Throwable) {
                    println("")

                }

            })
    }

    /**
     * All button click event
     */
    override fun onClick(view: View?) {
        var query = ""
        if(view == binding.btnHangout){
            query= "hangouts"
        } else if (view == binding.btnHotel){
            query="hotels"
        } else if (view == binding.btnShopping){
            query = "shopping"
        } else if (view == binding.btnRestaurant){
            query = "restaurants"
        }
        var intent = Intent(mContext, GoogleMapsActivity::class.java)
        intent.putExtra("query", query)
        intent.putExtra("attraction_name", attraction.name)
        intent.putExtra("latitude", placeDetailResult.geometry.location.lat)
        intent.putExtra("longtitude", placeDetailResult.geometry.location.lng)
        startActivity(intent)
    }


}