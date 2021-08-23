package team.mobileb.opgg.api.model

import com.google.gson.annotations.SerializedName
import team.mobileb.opgg.model.RoomInfo

data class RoomInfoResponse(

    @SerializedName("code")
    val _code: Number,
    @SerializedName("message")
    val _message: String,
    @SerializedName("result")
    val _result: ResultResponse,
    @SerializedName("responseTime")
    val _responseTime: String,

    ) : RoomInfo{
    override val code: Number get() = _code
    override val message: String get() = _message
    override val result: ResultResponse get() = _result
    override val responseTime: String get() = _responseTime
}