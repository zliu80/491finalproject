package com.zql.travelassistant.bean

data class Plans(
    var id: Int,
    var city_id: String,
    var city_name:String,
    var user_id:String
)

data class PlansDetail(
    var id:Int,
    var plans_id:Int,
    var attraction_id:String,
    var attraction_name:String,
    var start_date:String,
    var end_date:String,
    var description:String)
