package team.mobileb.opgg.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import team.mobileb.opgg.api.model.PositionInfoResponse

interface UtilsApi {
    @Headers(
        "Content-Type: application/hal+json",
        "Accept: application/hal+json"
    )
    @GET("/utils/position")
    fun retrievePosition(): Response<PositionInfoResponse>
}