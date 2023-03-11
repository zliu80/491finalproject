package com.zql.travelassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.databinding.ActivitySignUpBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.http.model.SignUpData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val mContext = this


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
                } else if (response.code() == 400){
                    // Sign up failed
                    var ebody = response.errorBody()
                    Log.e("Error code 400",response.errorBody().toString());
                }
                println(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("Travel Assistant SignUp" , "Failed")
                Toast.makeText(mContext, "Cannot connect to the server", Toast.LENGTH_SHORT).show()
            }

        })
    }
}