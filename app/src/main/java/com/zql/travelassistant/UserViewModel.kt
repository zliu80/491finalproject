package com.zql.travelassistant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zql.travelassistant.bean.UserRecord

class UserViewModel: ViewModel() {

    var userMutableLiveData:MutableLiveData<UserRecord> = MutableLiveData()


    fun setUser(userRecord: UserRecord){
        userMutableLiveData.postValue(userRecord)
    }

    fun getUser(): UserRecord? {
        return userMutableLiveData.value
    }
}