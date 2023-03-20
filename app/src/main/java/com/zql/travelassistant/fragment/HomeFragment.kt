package com.zql.travelassistant.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.zql.travelassistant.CityDetailActivity
import com.zql.travelassistant.R
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.adapter.HomeRecyclearViewAdapter
import com.zql.travelassistant.bean.Auth
import com.zql.travelassistant.bean.City
import com.zql.travelassistant.bean.CityList
import com.zql.travelassistant.databinding.FragmentHomeBinding
import com.zql.travelassistant.databinding.FragmentSettingsBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.http.model.UserBadResponse
import com.zql.travelassistant.http.util.BadResponseHandler
import com.zql.travelassistant.interfaces.OnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        initViews()
        cityList()
        return binding.root
    }

    private fun initViews() {
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun cityList(){
        RetrofitClient.api.cities().enqueue(object: Callback<CityList> {
            override fun onResponse(call: Call<CityList>, response: Response<CityList>) {
                if(response.code() == 200){
                    println(response.body())

                    val cityList: CityList? = response.body()

                    if (cityList != null) {
                        var layoutManager:RecyclerView.LayoutManager?= null
                        // Dynamically choose layout based on the orientation of device
                        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                            layoutManager = GridLayoutManager(context,3)
                            layoutManager.orientation = LinearLayoutManager.VERTICAL
                        } else  if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                            layoutManager = LinearLayoutManager(context)
                        }
                        binding.homeRecyclerview.layoutManager = layoutManager
                        val adapter = HomeRecyclearViewAdapter(context, cityList.items)
                        adapter.setOnItemClickListener(recyclerViewItemClickListener)
                        binding.homeRecyclerview.adapter = adapter

                    }
                } else {
                    // Handle 400, 403, 404 fail
                    // Sign up failed
                    BadResponseHandler.handleErrorResponse(context, response, "Fetching the city list failed!")
                }
            }

            override fun onFailure(call: Call<CityList>, t: Throwable) {
                BadResponseHandler.handleFailtureResponse(context)
            }

        })
    }

    val recyclerViewItemClickListener= object : OnItemClickListener {
        override fun onItemClick(position: Int) {

            val adapter=binding.homeRecyclerview.adapter as HomeRecyclearViewAdapter

            val item = adapter.data[position]

            val intent = Intent(context, CityDetailActivity::class.java)
            intent.putExtra("city_detail", Gson().toJson(item))
            startActivity(intent)
        }
    }





}