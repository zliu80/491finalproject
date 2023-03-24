package com.zql.travelassistant.fragment

import android.content.Intent
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.zql.travelassistant.CityDetailActivity
import com.zql.travelassistant.adapter.HomeRecyclearViewAdapter
import com.zql.travelassistant.bean.CityList
import com.zql.travelassistant.databinding.FragmentHomeBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.util.BadResponseHandler
import com.zql.travelassistant.interfaces.OnItemClickListener
import com.zql.travelassistant.view.RecyclerViewOrientationManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

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
        showLoading()
        RetrofitClient.api.cities().enqueue(object: Callback<CityList> {
            override fun onResponse(call: Call<CityList>, response: Response<CityList>) {
                closeLoading()

                if(response.code() == 200){
                    println(response.body())

                    val cityList: CityList? = response.body()

                    if (cityList != null) {

                        binding.homeRecyclerview.layoutManager = RecyclerViewOrientationManager.getLayoutManager(resources, requireContext())
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
                closeLoading()
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