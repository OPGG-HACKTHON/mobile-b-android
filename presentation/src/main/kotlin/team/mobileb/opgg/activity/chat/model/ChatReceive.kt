package team.mobileb.opgg.activity.chat.model

import com.google.gson.annotations.SerializedName

data class ChatReceive(
    @field:SerializedName("positionName")
    val positionName: String = "",

    @field:SerializedName("created_at_str")
    val createdAtStr: String = "",

    @field:SerializedName("positionType")
    val positionType: Int = 0,

    @field:SerializedName("messageType")
    val messageType: String = "",

    @field:SerializedName("userKey")
    val userKey: String = "",

    @field:SerializedName("content")
    val content: String = ""
)
