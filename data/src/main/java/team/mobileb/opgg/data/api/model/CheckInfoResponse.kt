package team.mobileb.opgg.data.api.model

import com.google.gson.annotations.SerializedName

data class CheckInfoResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: CheckResultResponse,
    @SerializedName("responseTime")
    val responseTime: String,
)
