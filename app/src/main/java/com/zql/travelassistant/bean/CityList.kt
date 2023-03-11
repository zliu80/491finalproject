package com.zql.travelassistant.bean

data class CityList(
    var page: Int,
    var perPage: Int,
    var totalPages: Int,
    var totalItems: Int,
    var items:MutableList<City>
)

