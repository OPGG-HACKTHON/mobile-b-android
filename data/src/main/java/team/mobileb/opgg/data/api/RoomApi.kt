package team.mobileb.opgg.data.api

import retrofit2.Response
import retrofit2.http.*
import team.mobileb.opgg.data.api.model.CheckInfoResponse
import team.mobileb.opgg.data.api.model.RoomInfoResponse

interface RoomApi {

    @Headers(
        "Content-Type: application/hal+json",
        "Accept: application/hal+json"
    )
    @POST("/rooms")
    suspend fun createRoom(@Query("userKey") userKey: String): Response<RoomInfoResponse>

    @GET("/rooms/{userKey}")
    suspend fun retrieveRoom(@Path("userKey") userKey: String): Response<RoomInfoResponse>

    @GET("/rooms/check/{inviteCode}")
    suspend fun checkRoom(@Path("inviteCode") inviteCode: String): Response<CheckInfoResponse>
}