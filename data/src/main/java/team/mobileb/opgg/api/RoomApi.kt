package team.mobileb.opgg.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import team.mobileb.opgg.api.model.RoomInfoResponse

interface RoomApi {

    @POST("/rooms")
    suspend fun createRoom(@Body userKey : String) : RoomInfoResponse

    @GET()
    suspend fun retrieveRoom()

    @GET()
    suspend fun getRoom() : RoomInfoResponse
}