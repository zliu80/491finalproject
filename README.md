# 491finalproject - Travel Assistant by Zhiqiang Liu

# A client to server project
  
  client: Android and written in Kotlin
  
  server: Pocketbase
  
# How to start

  Since this project relys on a server, so you should have the pocketbase.
  
  For Mac user: the pocketbase is alread in the /server folder.
  
  For Windows user: donwnload by https://pocketbase.io/
  
# Start the server

      cd 491finalproject/server/
      
      ./pocketbase serve
  
      Restful api port:
      
      > Server started at: http://127.0.0.1:8090
      
      - REST API: http://127.0.0.1:8090/api/
      
      - Admin UI: http://127.0.0.1:8090/_/
      
 # Android project
  
    Important file: TsApplication.kt
    
            const val SERVER_ADDRESS = "http://10.0.2.2:8090"
    
    10.0.2.2 links to the http://127.0.0.1:8090 address, by Android emulator default, this is the localhost address.
    
    If running on a real device, change the server address.
    
 # Frameworks
 
     // Avatar ImageView
    implementation "io.getstream:avatarview-coil:1.0.7"
    // Image Loader
    implementation 'com.squareup.picasso:picasso:2.8'
    // Image Selector
    implementation 'com.github.dhaval2404:imagepicker:2.1'
    // Google map SDK
    implementation 'com.google.android.gms:play-services-maps:18.1.0'
    // Ads banner
    implementation 'io.github.youth5201314:banner:2.2.2'
    // Material Dialog Library
    implementation 'dev.shreyaspatil.MaterialDialog:MaterialDialog:2.2.3'
    // Lottie Animation Library
    implementation 'com.airbnb.android:lottie:4.2.2'
    // Swipe to delete menu Library
    implementation 'com.github.mcxtzhang:SwipeDelMenuLayout:V1.3.0'
    // Bottom sheet
    implementation 'com.github.andrefrsousa:SuperBottomSheet:2.0.0'
    // Expandable Textview
    implementation 'com.ms-square:expandableTextView:0.1.4'
    // Theme selector
    implementation 'com.github.garretyoder:Colorful:2.3.4'
    
  # APIs
  
    Google Maps: https://maps.googleapis.com/
    
    Weather API: https://weatherapi-com.p.rapidapi.com/
            
           
