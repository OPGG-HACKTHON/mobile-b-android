package team.mobileb.opgg.data.model

import com.google.gson.annotations.SerializedName

data class CheckResultResponse(
    @SerializedName("messageMapping")
    val messageMapping: String,
    @SerializedName("sendTo")
    val sendTo: String
)

