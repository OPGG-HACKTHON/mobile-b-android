package team.mobileb.opgg.api.model

import com.google.gson.annotations.SerializedName
import team.mobileb.opgg.model.RoomInfo

data class RoomInfoResponse(
    @SerializedName("code")
    val code: Number,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: ResultResponse,
    @SerializedName("responseTime")
    val responseTime: String
)
