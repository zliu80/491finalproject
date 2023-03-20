package com.zql.travelassistant.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zql.travelassistant.ProfileActivity
import com.zql.travelassistant.R
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.UserViewModel
import com.zql.travelassistant.adapter.SettingsRecyclearViewAdapter
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.databinding.FragmentSettingsBinding
import io.getstream.avatarview.coil.loadImage

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    private lateinit var binding:FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
//        initViews()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initViews()
    }

    private fun initViews(){
        // Get the user object
        val user: User? = TSApplication.userRecord


        binding.avatarView.loadImage(data = TSApplication.getAvatarHttpAddress())
        binding.nickname.setText(user?.nickname)
        binding.avatarView.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }

        var layoutManager:RecyclerView.LayoutManager?= null
        // Dynamically choose layout based on the orientation of device
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = GridLayoutManager(context,3)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
        } else  if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = LinearLayoutManager(context)
        }
        binding.settingsRecyclerview.layoutManager = layoutManager
        binding.settingsRecyclerview.adapter = SettingsRecyclearViewAdapter(context, mutableListOf("Theme","Language"))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

    }


}