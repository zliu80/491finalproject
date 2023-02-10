package com.zql.travelassistant.bean

class Auth {

    private lateinit var token: String

    private lateinit var record: UserRecord

    fun getUserRecord():UserRecord{
        return record
    }
}