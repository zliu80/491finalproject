package com.zql.travelassistant.bean


/**
 * The http response body will be :
 *
{
"location": {
"name": "Los Angeles",
"region": "California",
"country": "United States of America",
"lat": 34.05,
"lon": -118.24,
"tz_id": "America/Los_Angeles",
"localtime_epoch": 1679019078,
"localtime": "2023-03-16 19:11"
},
"current": {
"last_updated_epoch": 1679018400,
"last_updated": "2023-03-16 19:00",
"temp_c": 16.1,
"temp_f": 61,
"is_day": 0,
"condition": {
"text": "Partly cloudy",
"icon": "//cdn.weatherapi.com/weather/64x64/night/116.png",
"code": 1003
},
"wind_mph": 2.2,
"wind_kph": 3.6,
"wind_degree": 10,
"wind_dir": "N",
"pressure_mb": 1016,
"pressure_in": 30.01,
"precip_mm": 0,
"precip_in": 0,
"humidity": 60,
"cloud": 50,
"feelslike_c": 16.1,
"feelslike_f": 61,
"vis_km": 16,
"vis_miles": 9,
"uv": 3,
"gust_mph": 8.3,
"gust_kph": 13.3
}
}
 */
data class Weather(var location: Location, var current: Current)

data class Location(
    var name: String,
    var region: String,
    var country: String,
    var lat: Double,
    var lon: Double,
    var tz_id: String,
    var localtime_epoch: Long,
    var localtime: String
)

data class Current(
    var last_updated_epoch: Long,
    var last_updated: String,
    var temp_c: Double,
    var temp_f: Double,
    var is_day: Int,
    var condition: Condition,
    var wind_mph: Double,
    var wind_kph: Double,
    var wind_degree: Int,
    var wind_dir: String,
    var pressure_mb: Double,
    var pressure_in: Double,
    var precip_mm: Double,
    var precip_in: Double,
    var humidity: Int,
    var cloud: Int,
    var feelslike_c: Double,
    var feelslike_f: Double,
    var vis_km: Double,
    var vis_miles: Double,
    var uv: Int,
    var gust_mph: Double,
    var gust_kph: Double
)

data class Condition(var text: String, var icon: String, var code: Int)