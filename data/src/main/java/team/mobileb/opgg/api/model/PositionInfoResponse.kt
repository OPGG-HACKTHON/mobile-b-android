package team.mobileb.opgg.api.model

import com.google.gson.annotations.SerializedName

data class PositionInfoResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<PositionResultResponse>,
    @SerializedName("responseTime")
    val responseTime: String
)