package team.mobileb.opgg.activity.chat.util

import android.app.Activity
import team.mobileb.opgg.GameWaitingService
import team.mobileb.opgg.activity.chat.model.Chat
import team.mobileb.opgg.util.config.IntentConfig

fun Activity.provideChatItem() = Chat(
    inviteCode = intent.getStringExtra(IntentConfig.ChatActivityInviteCode)!!,
    positionType = intent.getIntExtra(IntentConfig.ChatActivityPositionType, 0),
    userKey = GameWaitingService.DeviceId
)
