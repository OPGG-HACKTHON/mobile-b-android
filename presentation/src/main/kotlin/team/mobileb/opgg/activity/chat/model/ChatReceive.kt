package team.mobileb.opgg.activity.chat.model

import com.google.gson.annotations.SerializedName
import team.mobileb.opgg.R

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
    var content: String = ""
) {
    fun getPositionIconRes(): Int {
        // Server PositionType
        //    - 1 : 탑
        //    - 2 : 정글
        //    - 3 : 미드
        //    - 4 : 원딜
        //    - 5 : 서폿
        return when (positionType) {
            1 -> R.drawable.ic_baseline_top_24
            2 -> R.drawable.ic_baseline_jungle_24
            3 -> R.drawable.ic_baseline_mid_24
            4 -> R.drawable.ic_baseline_bottom_24
            5 -> R.drawable.ic_baseline_support_24
            else -> throw IndexOutOfBoundsException()
        }
    }
}
