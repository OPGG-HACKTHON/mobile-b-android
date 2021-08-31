package team.mobileb.opgg.data.model

import com.google.gson.annotations.SerializedName

data class RoomInfoResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: ResultResponse,
    @SerializedName("responseTime")
    val responseTime: String
)