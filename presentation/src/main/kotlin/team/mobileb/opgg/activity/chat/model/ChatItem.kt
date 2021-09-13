package team.mobileb.opgg.activity.chat.model

data class ChatItem(
    val inviteCode: String,
    val positionType: Int,
    val userKey: String,
    val message: String,
    val messageType: String = "CHAT",
)
