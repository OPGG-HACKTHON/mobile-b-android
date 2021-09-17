package team.mobileb.opgg.activity.chat.map

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import team.mobileb.opgg.activity.chat.model.Chat
import ua.naiksoftware.stomp.StompClient
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val client: StompClient) : ViewModel() {
    fun sendPin(chat: Chat, offset: Offset, type: MarkingType) {
        val data = JSONObject().apply {
            put("userKey", chat.userKey) // 방 생성시 입력한 userKey
            put("positionType", (chat.positionType + 1).toString())
            put("content", "x :${offset.x}, y:${offset.y}, ${type.value}") // 메시지
            put("messageType", type.value.uppercase())
            put("destRoomCode", chat.inviteCode) // 방 생성시 입력한 inviteCode
        }
        client.send("/stream/chat/send", data.toString()).subscribe()
    }
}
