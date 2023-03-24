package com.zql.travelassistant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.zql.travelassistant.bean.Auth
import com.zql.travelassistant.databinding.ActivitySigninBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.util.BadResponseHandler
import com.zql.travelassistant.util.ErrorHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : BaseActivityWithNoTitle() {

    private lateinit var binding: ActivitySigninBinding


    private val TAG_REMEMBER_ME = "REMEMBER_MY_ACCOUNT"
    private val TAG_USERNAME = "REMEMBER_MY_USERNAME"
    private val TAG_PASSWORD = "REMEMBER_MY_PASSWORD"


    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            init()


    }

    /**
     * Init all views
     */
    override fun init() {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {
            var intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            var username = binding.textUsername.editText?.text.toString()
            var password = binding.textPassword.editText?.text.toString()
            login(username, password)
        }

        if(getSharedPreferences(TAG,MODE_PRIVATE).getBoolean(TAG_REMEMBER_ME,false)){
            binding.switchRememberme.isChecked=true
            binding.textUsername.editText?.setText(getSharedPreferences(TAG, MODE_PRIVATE).getString(TAG_USERNAME,""))
            binding.textPassword.editText?.setText(getSharedPreferences(TAG, MODE_PRIVATE).getString(TAG_PASSWORD,""))
        }

        binding.switchRememberme.setOnCheckedChangeListener { compoundButton, isChecked ->
            getSharedPreferences(TAG, MODE_PRIVATE).edit().putBoolean(TAG_REMEMBER_ME, isChecked).commit()
            rememberMe(isChecked)
        }
    }

    private fun rememberMe(isChecked:Boolean){
        if (isChecked){
            getSharedPreferences(TAG, MODE_PRIVATE).edit().putString(TAG_USERNAME, binding.textUsername.editText?.text.toString()).commit()
            getSharedPreferences(TAG, MODE_PRIVATE).edit().putString(TAG_PASSWORD, binding.textPassword.editText?.text.toString()).commit()
        } else {
            getSharedPreferences(TAG, MODE_PRIVATE).edit().putString(TAG_USERNAME, "").commit()
            getSharedPreferences(TAG, MODE_PRIVATE).edit().putString(TAG_PASSWORD, "").commit()
        }
    }

    /**
     * Call the backend restful api through the Retrofit interface
     * login input: username/email, password
     */
    private fun login(username: String, password: String) {
        // Show the process bar to let user know it is logging
//        binding.progressLogin.visibility = View.VISIBLE
       showLoading()

        // Make a call to request the back-end server
        RetrofitClient.api.logIn(username, password).enqueue(object : Callback<Auth> {
            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
//                binding.progressLogin.visibility = View.INVISIBLE
                closeLoading()
                if (response.code() == 200 && response.isSuccessful) {
                    // Login success
                    val auth: Auth? = response.body()
                    // Assign the user record the Application
                    TSApplication.userRecord = auth!!.record
                    Log.d("Login success", response.body().toString())

                    // update new username and password
                    rememberMe(binding.switchRememberme.isChecked)

                    // Start the Main activity
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    startActivity(intent)
                    // Kill this activity
                    finish()
                } else {
                    // Handle 400 fail
                    // Login failed
                    BadResponseHandler.handleErrorResponse(mContext, response, "Login failed!")
                }
                println(response.body())
            }

            override fun onFailure(call: Call<Auth>, t: Throwable) {
                BadResponseHandler.handleFailtureResponse(mContext)
                closeLoading()
            }

        })
    }
}