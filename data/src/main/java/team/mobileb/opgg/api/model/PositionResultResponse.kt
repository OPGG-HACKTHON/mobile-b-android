package team.mobileb.opgg.api.model

import com.google.gson.annotations.SerializedName

data class PositionResultResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: String
)
