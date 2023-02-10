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
//            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)

        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initView(){

        binding.bottomNavigationView.setOnItemSelectedListener{
            when (it.itemId){
                R.id.home ->{
                    setTitle(R.string.home)
                    loadFragment(HomeFragment())

                    return@setOnItemSelectedListener true
                }
                R.id.favorite ->{
                    setTitle(R.string.plans)
                    loadFragment(PlansFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.currency ->{
                    setTitle(R.string.setting)
                    loadFragment(SettingsFragment())
                    return@setOnItemSelectedListener true

                }

            }
            false

        }
    }
}