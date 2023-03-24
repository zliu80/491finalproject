package com.zql.travelassistant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.zql.travelassistant.databinding.ActivityMainBinding
import com.zql.travelassistant.fragment.HomeFragment
import com.zql.travelassistant.fragment.PlansFragment
import com.zql.travelassistant.fragment.SettingsFragment
import com.zql.travelassistant.util.ErrorHandler

class MainActivity : BaseActivityWithNoTitle() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun init(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener{
            when (it.itemId){
                R.id.home ->{
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.favorite ->{
                    loadFragment(PlansFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.currency ->{
                    loadFragment(SettingsFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false

        }
    }
}