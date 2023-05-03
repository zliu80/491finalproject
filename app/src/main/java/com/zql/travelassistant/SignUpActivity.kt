package com.zql.travelassistant

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.databinding.ActivitySignUpBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.http.model.SignUpData
import com.zql.travelassistant.util.BadResponseHandler
import com.zql.travelassistant.util.ErrorHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivityWithNoTitle() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

    }

    override fun init() {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {

            var username: String = binding.textUsername.editText?.text.toString()
            var password: String = binding.textPassword.editText?.text.toString()
            var email: String = binding.textEmail.editText?.text.toString()

            if (validate()) {
                signUp(username, password, email)
            }
        }


    }

    private fun validate(): Boolean {
        var pwd = binding.textPassword.editText?.text.toString()
        var pwdConfirm = binding.textPasswordConfirm.editText?.text.toString()
        if (pwd != pwdConfirm) {
            Toast.makeText(mContext, "The passwords are not the same", Toast.LENGTH_SHORT).show()
            return false
        } else if(pwd.length <8 || pwd.length >72 || pwdConfirm.length<8 || pwdConfirm.length >72){
            Toast.makeText(mContext, "The length of password is limited between 8 and 72", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    /**
     * Call the backend restful api through the Retrofit interface
     * Sign up input: username, password, email
     */
    private fun signUp(username: String, password: String, email: String) {
        showLoading()
        // Entity set up
        val dataModel = SignUpData(username, email, true, password, password, false, "", 0)
        // Make a call to request the back-end server
        RetrofitClient.api.signUp(dataModel).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                closeLoading()
                if (response.code() == 200) {
                    // Sign up succeed
                    Toast.makeText(mContext, "Sign up success, you may log in", Toast.LENGTH_SHORT)
                        .show()
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
                closeLoading()
                BadResponseHandler.handleFailtureResponse(mContext)
            }

        })
    }
}