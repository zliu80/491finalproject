package com.zql.travelassistant

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.databinding.ActivityProfileBinding
import com.zql.travelassistant.http.RetrofitClient
import com.zql.travelassistant.http.model.UpdateUserAvatar
import com.zql.travelassistant.http.model.UpdateUserData
import com.zql.travelassistant.util.BadResponseHandler
import com.zql.travelassistant.util.ErrorHandler
import com.zql.travelassistant.util.MaterialDialog
import io.getstream.avatarview.coil.loadImage
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileActivity : BaseActivityWithTitle() {

    private lateinit var binding: ActivityProfileBinding


    /**
     * Init all views
     */
    override fun init() {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Get user entity from Application
        val user: User? = TSApplication.getUser(this)
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
            updateProcfile(
                user!!.id,
                UpdateUserData(
                    binding.textEmail.text.toString(),
                    binding.textNickname.text.toString(),
                    binding.textAge.text.toString().toInt(),
                    binding.textPhoneNumber.text.toString()
                )
            )
        }
    }

    /**
     * Click the back button will end this Activity
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
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

            var data: UpdateUserAvatar = UpdateUserAvatar(File(uri.path))
            updateProcfile(TSApplication.getUser(this)!!.id, data)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Update Procfile
     */
    private fun updateProcfile(id: String, data: UpdateUserData) {
        showLoading()
        RetrofitClient.api.updateUserProfile(id, data).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                closeLoading()
                if (response.code() == 200) {
                    TSApplication.userRecord = response.body()!!
//                    binding.avatarView.loadImage(data=TSApplication.getAvatarHttpAddress())
                    Log.d("Update profile success", response.body().toString())
                    MaterialDialog.show(
                        this@ProfileActivity,
                        "Your profile has been updated.",
                        "Success"
                    )
                } else {
                    // Handle 400, 403, 404 fail
                    // Sign up failed
                    BadResponseHandler.handleErrorResponse(
                        mContext,
                        response,
                        "Update profile failed!"
                    )
                }
                println(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                closeLoading()
                BadResponseHandler.handleFailtureResponse(mContext)
            }

        })
    }

    /**
     * Update User avatar
     */
    private fun updateProcfile(id: String, data: UpdateUserAvatar) {
        var part: MultipartBody.Part = MultipartBody.Part.createFormData(
            "avatar",
            data.avatar.name,
            data.avatar.asRequestBody()
        )
        showLoading()
        RetrofitClient.api.updateUserAvatar(id, part).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                closeLoading()
                if (response.code() == 200) {
                    TSApplication.userRecord = response.body()!!
                    binding.avatarView.loadImage(data = TSApplication.getAvatarHttpAddress())
                    Log.d("Update avatar success", response.body().toString())
                } else {
                    // Handle 400, 403, 404 fail
                    // Sign up failed
                    BadResponseHandler.handleErrorResponse(
                        mContext,
                        response,
                        "Update avatar failed!"
                    )
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