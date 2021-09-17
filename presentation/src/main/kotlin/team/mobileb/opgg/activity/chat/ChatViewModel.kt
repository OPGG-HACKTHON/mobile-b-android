package team.mobileb.opgg.activity.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import org.json.JSONObject
import team.mobileb.opgg.activity.chat.model.ChatItem
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompHeader
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val client: StompClient) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun connect(inviteCode: String) = callbackFlow {
        val topic = client.topic("/topic/message/$inviteCode").subscribe { topicMessage ->
            trySend(topicMessage.payload)
        }

        awaitClose { topic.dispose() }
    }.shareIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed())

    fun sendChat(chatItem: ChatItem) {
        val headerList = mutableListOf<StompHeader>().apply {
            add(StompHeader("inviteCode", chatItem.inviteCode)) // 방 생성시 입력한 inviteCode
            add(StompHeader("username", chatItem.userKey)) // 방 생성시 입력한 userKey
            // 클라이언트 index 0 부터 시작, 서버 index 1 부터 시작
            add(StompHeader("positionType", (chatItem.positionType + 1).toString()))
        }
        client.connect(headerList)

        // Server PositionType
        //    - 1 : 탑
        //    - 2 : 정글
        //    - 3 : 미드
        //    - 4 : 원딜
        //    - 5 : 서폿

        val data = JSONObject().apply {
            put("userKey", chatItem.userKey) // 방 생성시 입력한 userKey
            put("positionType", (chatItem.positionType + 1).toString())
            put("content", chatItem.message) // 메시지
            put("messageType", chatItem.messageType)
            put("destRoomCode", chatItem.inviteCode) // 방 생성시 입력한 inviteCode
        }

        client.send("/stream/chat/send", data.toString()).subscribe()
    }
}
