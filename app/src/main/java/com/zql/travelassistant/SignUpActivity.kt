package com.zql.travelassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zql.travelassistant.bean.UserRecord
import com.zql.travelassistant.databinding.ActivitySignUpBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.http.model.SignUpData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews(){
        binding.btnSignUp.setOnClickListener {

            var username:String = binding.textUsername.editText?.text.toString()
            var password:String = binding.textPassword.editText?.text.toString()
            var email:String = binding.textEmail.editText?.text.toString()

            signUp(username, password, email)

        }


    }

    private fun signUp(username:String, password:String, email:String){
        val api = RetrofitClient.api

        val dataModel = SignUpData(username, email, true, password, password, false, "", 0)
        api.SignUp(dataModel).enqueue(object: Callback<UserRecord> {
            override fun onResponse(call: Call<UserRecord>, response: Response<UserRecord>) {
                if(response.code() == 400)
                {
                    // Login failed
                    var ebody = response.errorBody()
                    Log.v("Error code 400",response.errorBody().toString());

                } else if (response.code() == 200){
                    // Login succeed
                }
                println(response.body())
            }

            override fun onFailure(call: Call<UserRecord>, t: Throwable) {
                Log.e("Travel Assistant SignUp" , "Failed")
            }

        })
    }
}