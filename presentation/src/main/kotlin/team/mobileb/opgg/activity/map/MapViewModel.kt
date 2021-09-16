package team.mobileb.opgg.activity.map

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import team.mobileb.opgg.activity.chat.model.ChatItem
import ua.naiksoftware.stomp.StompClient
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val client: StompClient): ViewModel() {

    var offset = mutableStateOf(Offset(0.0f, 0.0f))

    fun sendWarningPin(userKey : String, inviteCode : String, positionType: Int){
        Log.i("offset", "x:${offset.value.x}, y:${offset.value.y}")
        val data = JSONObject()
        data.put("userKey", userKey) // 방 생성시 입력한 userKey
        data.put("positionType", (positionType + 1).toString())
        data.put("content", "x :${offset.value.x}, y:${offset.value.y}, ward") // 메시지
        data.put("messageType", "WARD")
        data.put("destRoomCode", inviteCode) // 방 생성시 입력한 inviteCode
        client.send("/stream/chat/send", data.toString()).subscribe()
    }

}