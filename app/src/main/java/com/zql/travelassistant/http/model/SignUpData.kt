package com.zql.travelassistant.http.model

/**
 * The data model for siging up
 */
data class SignUpData (

    var username:String,

    var email:String,

    var emailVisibility:Boolean,

    var password:String,

    var passwordConfirm:String,

    var verified:Boolean,

    var nickname:String,

    var age:Int
)