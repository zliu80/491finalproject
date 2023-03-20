package com.zql.travelassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zql.travelassistant.databinding.ActivityMainBinding
import com.zql.travelassistant.fragment.HomeFragment
import com.zql.travelassistant.fragment.PlansFragment
import com.zql.travelassistant.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
        initView()
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initView(){
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