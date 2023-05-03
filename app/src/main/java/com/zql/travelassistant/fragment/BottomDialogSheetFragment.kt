package com.zql.travelassistant.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.indicator.CircleIndicator
import com.zql.travelassistant.R
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.adapter.AttractionReviewsRecyclearViewAdapter
import com.zql.travelassistant.adapter.CityDetailBannerImageAdapter
import com.zql.travelassistant.adapter.PlaceDetailBannerImageAdapter
import com.zql.travelassistant.bean.PlaceDetail
import com.zql.travelassistant.bean.PlaceDetailResult
import com.zql.travelassistant.bean.Results
import com.zql.travelassistant.databinding.FragmentBottomSheetPlaceDetailBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.interfaces.OnItemClickListener
import com.zql.travelassistant.util.BadResponseHandler
import com.zql.travelassistant.view.RecyclerViewOrientationManager
import dev.shreyaspatil.MaterialDialog.AbstractDialog
import dev.shreyaspatil.MaterialDialog.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Collections
import java.util.Date
import kotlin.time.Duration.Companion.days

open class BottomDialogSheetFragment(item:Results): SuperBottomSheetFragment(), OnClickListener {

    lateinit var binding:FragmentBottomSheetPlaceDetailBinding

    var item: Results

    lateinit var placeDetail:PlaceDetailResult

    init{
        this.item = item
    }

    @SuppressLint("SetTextI18n")
    private fun updateAttractionDetail() {
        binding.textPlaceName.text = placeDetail.name
        binding.textAddress.text = placeDetail.formatted_address
        binding.textPhoneNumber.text = placeDetail.formatted_phone_number

        binding.ratingBar.rating = placeDetail.rating

        binding.textWebsite.setOnClickListener(this)

        binding.textWebsite.text = placeDetail.website
        binding.textRating.text = placeDetail.rating.toString()
        binding.textRatingTotal.text = "("+placeDetail.user_ratings_total +")"

        if(placeDetail!=null && placeDetail.photos!=null){
            var photos = mutableListOf<String>()
            for (photo in placeDetail.photos){
                var url = "https://maps.googleapis.com/maps/api/place/photo" +
                        "?maxwidth=400" +
                        "&photo_reference=" + photo.photo_reference + " "  +
                        "&key=" + TSApplication.GOOGLE_MAPS_API_KEY
                photos.add(url)

            }
            val adapter = PlaceDetailBannerImageAdapter(photos)
            binding.banner.addBannerLifecycleObserver(this).setAdapter(adapter).indicator =
                CircleIndicator(requireContext())
            adapter.setOnItemClickListener(bannerItemClickListener)
        }




       showCurrentOpenningHours()

        if (placeDetail.reviews != null) {
            val adapter = AttractionReviewsRecyclearViewAdapter(requireContext(), placeDetail.reviews)
            adapter.setOnItemClickListener(recyclerViewItemClikListener)
            binding.recyclerView.layoutManager = RecyclerViewOrientationManager.getLayoutManager(resources, requireContext())
            binding.recyclerView.adapter = adapter
        } else {
            binding.textReviews.visibility = View.INVISIBLE
        }
    }

    private fun showCurrentOpenningHours(){
        if(placeDetail.current_opening_hours!=null){
            var calendar = Calendar.getInstance()
            var day = calendar.get(Calendar.DAY_OF_WEEK)
            var list = placeDetail.current_opening_hours.weekday_text
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
     * Banner Item click event
     */
    private val bannerItemClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {
//            val adapter = binding.banner.adapter as CityDetailBannerImageAdapter
        }
    }

    override fun onClick(view: View?) {
        if(view == binding.textWebsite){
            val uri:Uri = Uri.parse(placeDetail.website)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    private fun loadAttractionDetail(place_id: String) {
        var mDialog: AbstractDialog? = MaterialDialog.Builder(requireActivity()).setAnimation(R.raw.lottie_loading_animation).build()

        mDialog?.show()
        // search filter
        var filter =
            "formatted_address,formatted_phone_number,name,rating,user_ratings_total,geometry,current_opening_hours,icon,price_level,reservable,website,takeout,photos,reviews"
        RetrofitClient.api.placeDetail(filter, place_id, TSApplication.GOOGLE_MAPS_API_KEY)
            .enqueue(object : Callback<PlaceDetail> {
                override fun onResponse(call: Call<PlaceDetail>, response: Response<PlaceDetail>) {
                    mDialog?.cancel()
                    if (response.code() == 200) {
                        placeDetail = response.body()!!.result
                        updateAttractionDetail()
                        Log.d("Got the place detail", response.body().toString())
                    } else {
                        mDialog?.cancel()
                        // Handle 400, 403, 404 fail
                        // Sign up failed
                        BadResponseHandler.handleErrorResponse(
                            context, response, "Cannot load the weather."
                        )
                    }
                    println(response.body())
                }

                override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {
                    mDialog?.show()
                    BadResponseHandler.handleFailtureResponse(context)
                }

            })
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // the layout for the dialog
        binding = FragmentBottomSheetPlaceDetailBinding.inflate(layoutInflater)
        loadAttractionDetail(item.place_id)
        binding.expandTextView.setOnExpandStateChangeListener { textView, isExpanded ->
            showCurrentOpenningHours()
        }
        return binding.root
    }

    private val recyclerViewItemClikListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {

        }
    }

    override fun getPeekHeight(): Int {

        super.getPeekHeight()

        with(resources.displayMetrics) {
            return heightPixels - 300
        }

    }

}