package team.mobileb.opgg.api.model

import com.google.gson.annotations.SerializedName
import team.mobileb.opgg.model.Result

data class ResultResponse(
    @SerializedName("roomSeq")
    val roomSeq : Number,
    @SerializedName("userKey")
    val userKey: String,
    @SerializedName("inviteCode")
    val inviteCode : String,
    @SerializedName("inviteURL")
    val inviteURL : String,
    @SerializedName("createdAtStr")
    val createdAtStr : String
)

