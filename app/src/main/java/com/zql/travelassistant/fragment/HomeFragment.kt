package com.zql.travelassistant.fragment

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zql.travelassistant.R
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.bean.Auth
import com.zql.travelassistant.bean.City
import com.zql.travelassistant.bean.CityList
import com.zql.travelassistant.databinding.FragmentHomeBinding
import com.zql.travelassistant.databinding.FragmentSettingsBinding
import com.zql.travelassistant.http.RetrofitClient
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

    fun cityList(){
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
                        binding.homeRecyclerview.adapter = HomeRecyclearViewAdapter(context, cityList.items)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<CityList>, t: Throwable) {
            }

        })
    }


    inner class HomeRecyclearViewAdapter(val context: Context?, var data:MutableList<City>):
        RecyclerView.Adapter<HomeRecyclearViewAdapter.ViewHolder>(){

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val cityImage: ImageView = view.findViewById(R.id.img_recycler_view_item)
            val cityTitle: TextView = view.findViewById(R.id.title)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclearViewAdapter.ViewHolder {
            val view  = LayoutInflater.from(context).inflate(R.layout.item_home_recyclerview, parent, false)
            return ViewHolder(view)
        }


        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(vh: ViewHolder, position: Int) {
            var abosoluteCityImageUrl = TSApplication.CITY_FILE_PATH + data.get(position).collectionId + "/"
            abosoluteCityImageUrl += data.get(position).id +"/" + data.get(position).city_image
            println(abosoluteCityImageUrl)

            Picasso.get().load(abosoluteCityImageUrl).into(vh.cityImage)

            vh.cityTitle.setText(data.get(position).name)
        }

    }
}