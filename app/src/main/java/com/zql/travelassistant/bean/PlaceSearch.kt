package com.zql.travelassistant.bean


/**
 * The http response body will be :
 *

 */
data class PlaceSearch(
    var candidates: List<Candidates>,
    var status: String,
)

data class Candidates(var place_id: String)

data class PlaceDetail(
    var status: String,
    var result: PlaceDetailResult
)

//data class Candidates(var formatted_address:String, var geometry: Geometry, var name:String, var opening_hours:OpenningHours, var rating:Double)
data class PlaceDetailResult(
    var formatted_address: String,
    var formatted_phone_number: String,
    var geometry: Geometry, var name: String,
//      var opening_hours:OpenningHours,
    var rating: Float,
    var user_ratings_total:Int,
    var current_opening_hours: CurrentOpenningHours,
    var reviews: List<Reviews>,
    var icon:String,
    var price_level:Int,
    var reservable:Boolean,
    var website:String,
    var takeout:Boolean,
    var photos:List<PlacePhotos>
)

data class PlacePhotos(
    var height:Int,
    var width:Int,
    var html_attributions:List<String>,
    var photo_reference:String,

)

data class CurrentOpenningHours(
    var open_now: Boolean,
    var periods: List<Periods>,
    var weekday_text: List<String>
)

data class Periods(var close: Date, var open: Date)
data class Date(var date: String, var day: Int, var time: String)

data class Reviews(
    var author_name: String, var author_url: String,
    var language: String,
    var original_language: String,
    var profile_photo_url: String,
    var rating: Double,
    var relative_time_description: String,
    var text: String,
    var time: Long,
    var translated: Boolean
)