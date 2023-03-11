package com.zql.travelassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.zql.travelassistant.bean.Auth
import com.zql.travelassistant.databinding.ActivitySignUpBinding
import com.zql.travelassistant.databinding.ActivitySigninBinding
import com.zql.travelassistant.fragment.SettingsViewModel
import com.zql.travelassistant.http.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.HTTP

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding

    private val TAG = "TRAVEL_ASSISTANT"

    private lateinit var viewModel: UserViewModel

    private val mContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {
        binding.btnSignUp.setOnClickListener {
            var intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            var username = binding.textUsername.editText?.text.toString()
            var password = binding.textPassword.editText?.text.toString()
            login(username, password)
        }

    }

    /**
     * Call the backend restful api through the Retrofit interface
     * login input: username/email, password
     */
    private fun login(username: String, password: String) {
        // Show the process bar to let user know it is logging
        binding.progressLogin.visibility = View.VISIBLE
        // Make a call to request the back-end server
        RetrofitClient.api.logIn(username, password).enqueue(object : Callback<Auth> {
            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                binding.progressLogin.visibility = View.INVISIBLE
                if (response.code() == 200 ) {
                    // Login success
                    val auth: Auth? = response.body()
                    // Assign the user record the Application
                    TSApplication.userRecord = auth!!.record
                    Log.d("Login success", response.errorBody().toString());
                    // Start the Main activity
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    startActivity(intent)
                    // Kill this activity
                    finish()
                } else if (response.code() == 400) {
                    // Login failed
                    var ebody = response.errorBody()
                    Log.e("Error code 400", response.errorBody().toString());
                    Toast.makeText(mContext, "Login failed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(mContext, "Login failed", Toast.LENGTH_SHORT).show()
                    Log.e("Error code 400", response.errorBody().toString());
                }
                println(response.body())
            }

            override fun onFailure(call: Call<Auth>, t: Throwable) {
                Log.e("Travel Assistant", "Failed")
                Toast.makeText(mContext, "Cannot connect to the server", Toast.LENGTH_SHORT).show()
                binding.progressLogin.visibility = View.INVISIBLE
            }

        })
    }
}