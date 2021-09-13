package team.mobileb.opgg.data.api

import retrofit2.Response
import retrofit2.http.*
import team.mobileb.opgg.data.model.CheckInfoResponse
import team.mobileb.opgg.data.model.RoomInfoResponse
import team.mobileb.opgg.data.model.UserInputData

interface RoomsApi {
    @Headers("Content-Type: application/json", "Host: localhost:8080")
    @POST
    suspend fun createRoom(@Body userInputData: UserInputData): Response<RoomInfoResponse>

    @GET("{userKey}")
    suspend fun retrieveRoom(@Path("userKey") userKey: String): Response<RoomInfoResponse>

    @GET("check/{inviteCode}")
    suspend fun checkRoom(@Path("inviteCode") inviteCode: String): Response<CheckInfoResponse>
}
