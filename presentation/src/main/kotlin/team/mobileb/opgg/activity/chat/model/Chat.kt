package team.mobileb.opgg.activity.chat.model

data class Chat(
    val inviteCode: String,
    val positionType: Int,
    val userKey: String,
    val message: String = "",
    val messageType: String = "CHAT",
)
