package com.zql.travelassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.databinding.ActivitySignUpBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.http.model.SignUpData
import com.zql.travelassistant.http.model.UserBadResponse
import com.zql.travelassistant.http.util.BadResponseHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val mContext = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews(){
        binding.btnSignUp.setOnClickListener {

            var username:String = binding.textUsername.editText?.text.toString()
            var password:String = binding.textPassword.editText?.text.toString()
            var email:String = binding.textEmail.editText?.text.toString()

            if(validate()){
                signUp(username, password, email)
            }
        }


    }

    private fun validate():Boolean{
        if(binding.textPassword.editText?.text!= binding.textPasswordConfirm.editText?.text){
            Toast.makeText(mContext, "The passwords are not the same", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    /**
     * Call the backend restful api through the Retrofit interface
     * Sign up input: username, password, email
     */
    private fun signUp(username:String, password:String, email:String){
        // Entity set up
        val dataModel = SignUpData(username, email, true, password, password, false, "", 0)
        // Make a call to request the back-end server
        RetrofitClient.api.signUp(dataModel).enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code() == 200)
                {
                    // Sign up succeed
                    Toast.makeText(mContext, "Sign up success, you may log in", Toast.LENGTH_SHORT).show()
                    // Kill this activity
                    finish()
                } else {
                    // Handle 400, 403 fail
                    // Sign up failed
                    BadResponseHandler.handleErrorResponse(mContext, response, "Sign up failed!")
                }
                println(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                BadResponseHandler.handleFailtureResponse(mContext)
            }

        })
    }
}