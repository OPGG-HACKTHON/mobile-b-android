package team.mobileb.opgg.activity.chat.util

import android.app.Activity
import team.mobileb.opgg.GameWaitingService
import team.mobileb.opgg.activity.chat.model.Chat
import team.mobileb.opgg.util.config.IntentConfig

fun Activity.provideChatItem() = Chat(
    inviteCode = intent.getStringExtra(IntentConfig.ChatActivityInviteCode)!!,
    positionType = intent.getIntExtra(IntentConfig.ChatActivityPositionType, 0),
    messageType = "CHAT",
    userKey = GameWaitingService.DeviceId
)

fun Activity.provideMapItem() = Chat(
    inviteCode = intent.getStringExtra(IntentConfig.ChatActivityInviteCode)!!,
    positionType = intent.getIntExtra(IntentConfig.ChatActivityPositionType, 0),
    messageType = "WARD",
    userKey = GameWaitingService.DeviceId
)
