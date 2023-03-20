package com.zql.travelassistant.bean

data class CityList(
    var page: Int,
    var perPage: Int,
    var totalPages: Int,
    var totalItems: Int,
    var items:MutableList<City>
)

/**
 * The http response body will be :
 *
"id": "RECORD_ID",
"collectionId": "gdvmachyqq8428a",
"collectionName": "city",
"created": "2022-01-01 01:00:00.123Z",
"updated": "2022-01-01 23:59:59.456Z",
"name": "test",
"fun_facts": "test",
"rank": 123,
"description": "test",
"city_image": "filename.jpg"
 */
data class City (

    var id: String,
    var collectionId: String,
    var collectionName: String,
    var created: String,
    var updated: String,
    var name: String,
    var fun_fucts: Boolean = false,
    var rank: Int = 0,
    var description: String,
    var city_image: String,
)

