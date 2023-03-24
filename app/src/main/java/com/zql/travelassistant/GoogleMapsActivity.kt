package com.zql.travelassistant

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.zql.travelassistant.adapter.PlacesRecyclearViewAdapter
import com.zql.travelassistant.bean.Place
import com.zql.travelassistant.bean.Results
import com.zql.travelassistant.databinding.ActivityGoogleMapsBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.interfaces.OnItemClickListener
import com.zql.travelassistant.util.BadResponseHandler
import com.zql.travelassistant.util.ErrorHandler
import com.zql.travelassistant.view.RecyclerViewScrollHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoogleMapsActivity : BaseActivityWithTitle(), OnMapReadyCallback,
    OnMarkerClickListener, OnCameraMoveListener, GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveStartedListener {
    // Layout binding
    private lateinit var binding: ActivityGoogleMapsBinding

    // Google Map Android SDK
    private lateinit var mMap: GoogleMap

    // Google Map Restful search query
    private var query: String? = ""

    // Attraction name
    private var attraction_name: String? = ""

    // Address latitude
    private var latitude: Double = 0.0

    // Address longtitude
    private var longtitude: Double = 0.0

    // Map moving reason
    private var map_move_reason = -1

    // List of markers on Google Map
    private var listMarkers: MutableList<Marker> = mutableListOf()

    // Google Map displayed zoom
    private var MAP_ZOOM = 13f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {
        binding = ActivityGoogleMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        query = intent.getStringExtra("query")
        attraction_name = intent.getStringExtra("attraction_name")
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longtitude = intent.getDoubleExtra("longtitude", 0.0)
        // Set back button

        supportActionBar?.title = attraction_name

        loadPlaces(latitude, longtitude)
    }

    /**
     * Update the place list on the bottom of screen
     */
    private fun updatePlaces(list: List<Results>) {
        var layoutManager: RecyclerView.LayoutManager? = null
        // Show only 1 item
        layoutManager = GridLayoutManager(mContext, 1)
        // Set Horizontal
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = layoutManager
        var adapter = PlacesRecyclearViewAdapter(supportFragmentManager, mContext, list)
        binding.recyclerView.adapter = adapter
        // Set item click event
        adapter.setOnItemClickListener(recyclerViewItemClikListener)
    }

    /**
     * Place item click event on the bottom
     */
    private val recyclerViewItemClikListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {
            var marker = listMarkers.get(position)
            // Move the camera of Google map to center
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, MAP_ZOOM))
            // Show the marker information
            marker.showInfoWindow()
        }
    }

    /**
     * Restful Google Map API to load places based on the latitude and longtitude
     * Addtional query will be: "restraurant in main street"
     */
    private fun loadPlaces(latitude: Double, longtitude: Double) {
        var name = query + " in" + attraction_name
        RetrofitClient.api.searchPlaces(
            (latitude.toString() + "," + longtitude.toString()), name, query!!,
            TSApplication.GOOGLE_MAPS_API_KEY
        ).enqueue(object : Callback<Place> {
            override fun onResponse(call: Call<Place>, response: Response<Place>) {
                if (response.code() == 200) {
                    var list: List<Results>? = response.body()?.results
                    if (list != null) {
                        addMarkers(list)
                        updatePlaces(list)
                    }
                } else {
                    BadResponseHandler.handleErrorResponse(
                        mContext,
                        response,
                        "Cannot load the certain place."
                    )
                }
            }

            override fun onFailure(call: Call<Place>, t: Throwable) {
                Log.d("fail", "s")
                BadResponseHandler.handleFailtureResponse(mContext)
            }
        })
    }

    /**
     * Add markers to the Google Maps and show to user
     */
    private fun addMarkers(list: List<Results>) {
        // Everytime remove all markers first, because user move the map will cause reloading places
        for (marker in listMarkers) {
            marker.remove()
        }
        listMarkers.clear()

        for (i in list.indices) {
            var item = list.get(i)
            // Get the position
            var position = LatLng(item.geometry.location.lat, item.geometry.location.lng)
            // Add marker to Google Map
            var marker = mMap.addMarker(MarkerOptions().position(position).title(item.name))
            // Set a tag
            marker?.tag = i
            if (marker != null) {
                listMarkers.add(marker)
            }
        }

    }


    /**
     * Following are the Google Map Moving events
     */

    override fun onCameraMove() {
        Log.d(TAG, "camera is moving")
    }

    override fun onCameraIdle() {
        Log.d(TAG, "stop moving")
        if (map_move_reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Log.d(TAG, "moving reason: user gesture")
            // reload places
            loadPlaces(mMap.cameraPosition.target.latitude, mMap.cameraPosition.target.longitude)
        } else if (map_move_reason == GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION) {
            Log.d(TAG, "moving reason: api animation")
        } else if (map_move_reason == GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION) {
            Log.d(TAG, "moving reason: developer animation")
        }
    }

    override fun onCameraMoveStarted(reason: Int) {
        map_move_reason = reason

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val city = LatLng(latitude, longtitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, MAP_ZOOM))
        mMap.setOnMarkerClickListener(this)
        mMap.setOnCameraIdleListener(this)
        mMap.setOnCameraMoveListener(this)
        mMap.setOnCameraMoveStartedListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        var i: Int = marker.tag as Int
        RecyclerViewScrollHelper.scrollToPosition(binding.recyclerView, i)
        return false
    }
}

