package com.zql.travelassistant.bean


/**
 * The http response body will be :
 *
{"record":{"age":22,
            "avatar":"dsc01296_HPXsQ4oWj5.JPG",
            "collectionId":"_pb_users_auth_",
            "collectionName":"users",
            "created":"2023-02-03 06:02:59.363Z",
            "email":"testuser1234@test.com",
            "emailVisibility":false,"id":"4bv1gflxqhchuu8",
            "nickname":"csuf test user",
            "updated":"2023-02-23 23:58:54.779Z",
            "username":"testuser1234","verified":false},
            "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb2xsZWN0aW9uSWQiOiJfcGJfdXNlcnNfYXV0aF8iLCJleHAiOjE2Nzg0MDc3NjEsImlkIjoiNGJ2MWdmbHhxaGNodXU4IiwidHlwZSI6ImF1dGhSZWNvcmQifQ.PBGQ9W4yth02bAyWsYedwpGUguVSrRS7l9kVvVo3y-E"}
}
 */
data class User (

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
      var avatar: String,     // url
      var age: Int = 0

)