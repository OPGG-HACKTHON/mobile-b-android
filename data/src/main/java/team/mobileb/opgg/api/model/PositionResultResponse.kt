package team.mobileb.opgg.api.model

import com.google.gson.annotations.SerializedName

data class PositionResultResponse(
    @SerializedName("code")
    val code : Number,
    @SerializedName("name")
    val name : String
)
