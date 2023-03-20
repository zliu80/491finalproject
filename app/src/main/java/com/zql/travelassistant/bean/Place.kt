package com.zql.travelassistant.bean


/**
 * The http response body will be :
 *
{
"html_attributions": [],
"results":
[
{
"formatted_address": "Main St, Denver, CO 80238, USA",
"geometry":
{
"location": { "lat": 39.782267, "lng": -104.8919341 },
"viewport":
{
"northeast":
{ "lat": 39.78361682989273, "lng": -104.8905842701073 },
"southwest":
{ "lat": 39.78091717010729, "lng": -104.8932839298927 },
},
},
"icon": "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/geocode-71.png",
"icon_background_color": "#7B9EB0",
"icon_mask_base_uri": "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
"name": "Main St",
"place_id": "ChIJIS85_gd7bIcRJIGEPue1cJI",
"reference": "ChIJIS85_gd7bIcRJIGEPue1cJI",
"types": ["route"],
},
],
"status": "OK",
}
 */
data class Place (
      var id: String,
      var results:List<Results>,
      var status:String,
)
data class OpenningHours(var open_now: Boolean)

data class Results(var formatted_address:String, var geometry: Geometry, var icon:String, var icon_background_color:String, var icon_mask_base_uri:String, var name:String, var place_id:String, var reference:String,var types:List<String>,var opening_hours:OpenningHours)
data class Geometry(var location: Locations, var viewport: Viewport)
data class Locations(var lat:Double, var lng:Double)
data class Viewport(var northeast: Northeast, var southwest: Southwest)
data class Northeast(var lat:Double,var lng: Double)
data class Southwest(var lat:Double,var lng: Double)