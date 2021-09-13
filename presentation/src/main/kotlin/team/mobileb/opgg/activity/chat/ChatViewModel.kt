package team.mobileb.opgg.activity.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.StompHeader
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    fun runStomp() {
        val url = "ws://ec2-18-222-138-73.us-east-2.compute.amazonaws.com:3724/socket/websocket"
        val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

        stompClient.topic("/topic/message/test0912").subscribe { topicMessage ->
            Log.i("message Recieve", topicMessage.payload)
        }

        val headerList = arrayListOf<StompHeader>()
        headerList.add(StompHeader("inviteCode", "test0912")) // 방 생성시 입력한 inviteCode
        headerList.add(StompHeader("username", "20")) // 방 생성시 입력한 userKey
        headerList.add(StompHeader("positionType", "1"))
        stompClient.connect(headerList)

        val data = JSONObject()
        data.put("userKey", "20") // 방 생성시 입력한 userKey
        data.put("positionType", "1")
        data.put("content", "test") // 메시지
        data.put("messageType", "CHAT")
        data.put("destRoomCode", "test0912") // 방 생성시 입력한 inviteCode

        stompClient.send("/stream/chat/send", data.toString()).subscribe()
    }
}
