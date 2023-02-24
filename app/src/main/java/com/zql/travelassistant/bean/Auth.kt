package com.zql.travelassistant.bean

class Auth {

    private lateinit var token: String

    private lateinit var record: User

    fun getUserRecord():User{
        return record
    }
}