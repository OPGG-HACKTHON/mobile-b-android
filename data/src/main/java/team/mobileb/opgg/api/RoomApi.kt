package team.mobileb.opgg.api

import retrofit2.Response
import retrofit2.http.*
import team.mobileb.opgg.api.model.CheckInfoResponse
import team.mobileb.opgg.api.model.RoomInfoResponse

interface RoomApi {

    @POST("/rooms")
    suspend fun createRoom(@Query("userKey") userKey : String) : RoomInfoResponse
    @GET("/rooms/{userKey}")
    suspend fun retrieveRoom(@Path("userKey") userKey: String) : RoomInfoResponse
    @GET("/rooms/check/{inviteCode}")
    suspend fun checkRoom(@Path("inviteCode") inviteCode : String) : CheckInfoResponse
}