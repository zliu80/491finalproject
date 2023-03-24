package com.zql.travelassistant.bean

data class AttractionList(
    var page: Int,
    var perPage: Int,
    var totalPages: Int,
    var totalItems: Int,
    var items: MutableList<Attraction>
)

/**
"id": "RECORD_ID",
"collectionId": "9jkis8gsdoiyirt",
"collectionName": "attraction",
"created": "2022-01-01 01:00:00.123Z",
"updated": "2022-01-01 23:59:59.456Z",
"name": "test",
"description": "test",
"address": "test",
"city_id": "RELATION_RECORD_ID",
"attraction_image": "filename.jpg"
 */
data class Attraction(
    var id: String,
    var collectionId: String,
    var collectionName: String,
    var created: String,
    var updated: String,
    var name: String,
    var description: String,
    var address: String,
    var city_id: String,
    var attraction_image: String
)

