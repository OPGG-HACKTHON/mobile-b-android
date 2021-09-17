package team.mobileb.opgg.activity.room

import com.google.gson.annotations.SerializedName

data class RoomError(
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("responseTime")
    val responseTime: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("errorDetails")
    val errorDetails: List<String>
)
