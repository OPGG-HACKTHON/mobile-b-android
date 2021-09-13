package team.mobileb.opgg.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import team.mobileb.opgg.data.model.PositionInfoResponse

interface UtilsApi {
    @Headers("Content-Type: application/json", "Host: localhost:8080")
    @GET("position")
    suspend fun retrievePosition(): Response<PositionInfoResponse>
}
