package com.zql.travelassistant

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.databinding.ActivityProfileBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.http.model.UpdateUserAvatar
import com.zql.travelassistant.http.model.UpdateUserData
import com.zql.travelassistant.http.model.UserBadResponse
import com.zql.travelassistant.http.util.BadResponseHandler
import io.getstream.avatarview.coil.loadImage
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val mContext = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    /**
     * Init all views
     */
    private fun initViews() {
        // Set back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Get user entity from Application
        val user: User? = TSApplication.userRecord
        // Load the user avatar
        binding.avatarView.loadImage(data = TSApplication.getAvatarHttpAddress())
        binding.textNickname.setText(user?.nickname)
        binding.textEmail.setText(user?.email)
        binding.textAge.setText(user?.age.toString())
        binding.textPhoneNumber.setText(user?.phoneNumber)
        // Avatar icon click event
        binding.avatarView.setOnClickListener {
            avatarPick()
        }
        // Update profile button click event
        binding.btnUpdateProfile.setOnClickListener {
            updateProcfile(TSApplication.userRecord.id, UpdateUserData(binding.textEmail.text.toString(), binding.textNickname.text.toString(), binding.textAge.text.toString().toInt(), binding.textPhoneNumber.text.toString()))
        }
    }

    /**
     * Click the back button will end this Activity
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()

        }
        return super.onOptionsItemSelected(item)

    }

    /**
     * Let user to choose a new avatar
     */
    private fun avatarPick() {
        ImagePicker.with(this).crop().compress(1024).maxResultSize(1080, 1080).start()
    }

    /**
     * Handle the Avatar selector returned result
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            println(uri)

            var data:UpdateUserAvatar = UpdateUserAvatar(File(uri.path))
            updateProcfile(TSApplication.userRecord.id,data)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Update Procfile
     */
    private fun updateProcfile(id:String, data:UpdateUserData){
        RetrofitClient.api.updateUserProfile(id, data).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200 ) {
                    TSApplication.userRecord = response.body()!!
//                    binding.avatarView.loadImage(data=TSApplication.getAvatarHttpAddress())
                    Log.d("Update profile success", response.body().toString());
                } else {
                    // Handle 400, 403, 404 fail
                    // Sign up failed
                    BadResponseHandler.handleErrorResponse(mContext, response, "Update profile failed!")
                }
                println(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                BadResponseHandler.handleFailtureResponse(mContext)

            }

        })
    }

    /**
     * Update User avatar
     */
    private fun updateProcfile(id:String,data:UpdateUserAvatar){
        var part:MultipartBody.Part = MultipartBody.Part.createFormData("avatar",data.avatar.name, data.avatar.asRequestBody())

        RetrofitClient.api.updateUserAvatar(id, part).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200 ) {
                    TSApplication.userRecord = response.body()!!
                    binding.avatarView.loadImage(data=TSApplication.getAvatarHttpAddress())
                    Log.d("Update avatar success", response.body().toString());
                } else {
                    // Handle 400, 403, 404 fail
                    // Sign up failed
                    BadResponseHandler.handleErrorResponse(mContext, response, "Update avatar failed!")
                }
                println(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                BadResponseHandler.handleFailtureResponse(mContext)

            }

        })
    }
}