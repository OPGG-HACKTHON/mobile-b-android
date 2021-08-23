package team.mobileb.opgg.api.model

import com.google.gson.annotations.SerializedName
import team.mobileb.opgg.model.Result

data class ResultResponse(
    @SerializedName("roomSeq")
    val _roomSeq : Number,
    @SerializedName("userKey")
    val _userKey: String,
    @SerializedName("inviteCode")
    val _inviteCode : String,
    @SerializedName("inviteURL")
    val _inviteURL : String,
    @SerializedName("createdAtStr")
    val _createdAtStr : String,

) : Result {
    override val roomSeq: Number get() =  _roomSeq
    override val userKey: String get() =  _userKey
    override val inviteCode: String get() =  _inviteCode
    override val inviteURL: String get() =  _inviteURL
    override val createdAtStr: String get() =  _createdAtStr
}
