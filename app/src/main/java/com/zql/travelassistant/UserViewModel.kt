package com.zql.travelassistant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zql.travelassistant.bean.User

class UserViewModel: ViewModel() {

    var userMutableLiveData:MutableLiveData<User> = MutableLiveData()


    fun setUser(user: User){
        userMutableLiveData.postValue(user)
    }

    fun getUser(): User? {
        return userMutableLiveData.value
    }
}