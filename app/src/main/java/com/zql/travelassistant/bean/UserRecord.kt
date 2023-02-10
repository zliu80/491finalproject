package com.zql.travelassistant.bean


/**
 * The response body is :
 *
 * {
"data": {
"translations": [{"translatedText": "¡Hola Mundo!"}]
}
}
 */
data class UserRecord (

      var id: String,
      var collectionId: String,
      var collectionName: String,
      var created: String,
      var updated: String,
      var username: String,
      var verified: Boolean = false,
      var emailVisibility: Boolean = false,
      var email: String,
      var nickname: String,
      var avatar: String,
      var age: Int = 0

)