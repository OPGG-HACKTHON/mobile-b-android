package team.mobileb.opgg.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import team.mobileb.opgg.data.model.CheckInfoResponse
import team.mobileb.opgg.data.model.RoomInfoResponse
import team.mobileb.opgg.domain.model.CreateRoomData

interface RoomsApi {
    @Headers("Content-Type: application/json", "Host: localhost:8080")
    @POST
    suspend fun createRoom(@Body createRoomData: CreateRoomData): Response<RoomInfoResponse>

    @GET("{userKey}")
    suspend fun retrieveRoom(@Path("userKey") userKey: String): Response<RoomInfoResponse>

    @GET("check/{inviteCode}")
    suspend fun checkRoom(@Path("inviteCode") inviteCode: String): Response<CheckInfoResponse>
}
