package team.mobileb.opgg.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.mobileb.opgg.data.model.CheckInfoResponse
import team.mobileb.opgg.data.model.RoomInfoResponse

interface RoomsApi {
    @Headers("Content-Type: application/hal+json", "Accept: application/hal+json")
    @POST
    suspend fun createRoom(@Query("userKey") userKey: String): Response<RoomInfoResponse>

    @GET("{userKey}")
    suspend fun retrieveRoom(@Path("userKey") userKey: String): Response<RoomInfoResponse>

    @GET("check/{inviteCode}")
    suspend fun checkRoom(@Path("inviteCode") inviteCode: String): Response<CheckInfoResponse>
}
