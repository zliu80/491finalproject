package com.zql.travelassistant.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zql.travelassistant.UserViewModel
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    private lateinit var userViewModel: UserViewModel

    private lateinit var binding:FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        val user: User? = userViewModel.getUser()
        println(user?.avatar)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        userViewModel = ViewModelProvider(this).get((UserViewModel::class.java))

    }

}