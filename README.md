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
    
    
            
           
