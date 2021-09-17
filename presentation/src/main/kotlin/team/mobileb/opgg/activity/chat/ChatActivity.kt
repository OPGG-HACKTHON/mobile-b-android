package team.mobileb.opgg.activity.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import team.mobileb.opgg.GameWaitingService
import team.mobileb.opgg.R
import team.mobileb.opgg.activity.chat.map.MapActivity
import team.mobileb.opgg.activity.chat.map.MarkingType
import team.mobileb.opgg.activity.chat.model.ChatReceive
import team.mobileb.opgg.activity.chat.util.provideChatItem
import team.mobileb.opgg.activity.chat.util.provideMapItem
import team.mobileb.opgg.theme.Blue
import team.mobileb.opgg.theme.ChatColor
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.Pink
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.theme.transparentTextFieldColors
import team.mobileb.opgg.util.ColorUtil
import team.mobileb.opgg.util.config.IntentConfig
import team.mobileb.opgg.util.extension.toModel
import team.mobileb.opgg.util.extension.toast

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {
    private val MAP_CODE = 20
    private val chatVm: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatVm.connectSocket(provideChatItem())
        SystemUiController(window).run {
            setStatusBarColor(Blue)
            setNavigationBarColor(Color.White)
        }
        setContent {
            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Toolbar(
                            roomName = intent.getStringExtra(IntentConfig.ChatActivityInviteCode)!!
                        )
                    },
                    content = { Content() }
                )
            }
        }
    }

    @Composable
    private fun Toolbar(roomName: String) {
        TopAppBar {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (back, title) = createRefs()

                Icon(
                    painter = painterResource(R.drawable.ic_round_exit_24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clickable { finish() }
                        .constrainAs(back) {
                            start.linkTo(parent.start, 12.dp)
                        }
                )
                Text(
                    modifier = Modifier.constrainAs(title) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    text = roomName,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    private fun Content() {
        val defaultPadding = 16.dp
        val messageFieldTopPadding = 15.dp
        val defaultChatItem = provideChatItem()
        var messageField by remember { mutableStateOf(TextFieldValue()) }
        val messages = remember { mutableStateListOf<ChatReceive>() }
        val scrollState = rememberLazyListState()

        LaunchedEffect(Unit) {
            chatVm.connect(intent.getStringExtra(IntentConfig.ChatActivityInviteCode)!!)
                .collect { message ->
                    messages.add(message.toModel())

                    messages.forEach { message ->
                        if (message.content.contains("Warn")) {
                            val list = message.content.split(",")
                            val x = list[0].split(":")[1].toFloat()
                            val y = list[1].split(":")[1].toFloat()
                            intent.putExtra("warnOffsetX", list[0].split(":")[1].toFloat())
                            intent.putExtra("warnOffsetY", list[1].split(":")[1].toFloat())
                            chatVm.warnOffset.value = Offset(x, y)
                            Log.i("chatVM warn update", chatVm.warnOffset.value.toString())
                            message.content = "지도에 위험 핑을 업데이트 했습니다"

                        }
                        if (message.content.contains("Ward")) {
                            val list = message.content.split(",")
                            val x = list[0].split(":")[1].toFloat()
                            val y = list[1].split(":")[1].toFloat()
                            intent.putExtra("warnOffsetX", list[0].split(":")[1].toFloat())
                            intent.putExtra("warnOffsetY", list[1].split(":")[1].toFloat())
                            chatVm.wardOffset.value = Offset(x, y)

                            Log.i("chatVM ward update", chatVm.wardOffset.value.toString())
                            message.content = "지도에 와드 핑을 업데이트 했습니다"
                        }
                    }

                    scrollState.scrollToItem(messages.size - 1)
                }
        }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (lazyChats, fab, textField) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(lazyChats) {
                        top.linkTo(parent.top)
                        bottom.linkTo(textField.top, messageFieldTopPadding)
                        height = Dimension.fillToConstraints
                    },
                contentPadding = PaddingValues(defaultPadding),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                state = scrollState
            ) {
                itemsIndexed(items = messages) { index, message ->
                    ChatBubble(
                        prevItem = messages.getOrNull(index - 1),
                        item = message,
                        nextItem = messages.getOrNull(index + 1)
                    )
                }
            }
            FloatingActionButton(
                modifier = Modifier.constrainAs(fab) {
                    end.linkTo(parent.end, defaultPadding)
                    bottom.linkTo(textField.top, messageFieldTopPadding)
                },
                onClick = {
                    startMapActivity(
                        inviteCode = defaultChatItem.inviteCode,
                        positionType = defaultChatItem.positionType
                    )
                },
                backgroundColor = Pink
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_add_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            TextField(
                modifier = Modifier.constrainAs(textField) {
                    start.linkTo(parent.start, defaultPadding)
                    end.linkTo(parent.end, defaultPadding)
                    bottom.linkTo(parent.bottom, defaultPadding)
                    width = Dimension.fillToConstraints
                },
                shape = RoundedCornerShape(30.dp),
                value = messageField,
                onValueChange = { messageField = it },
                colors = transparentTextFieldColors(backgroundColor = ChatColor.TextFieldBackgroundColor),
                trailingIcon = {
                    Surface(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val message = messageField.text
                                if (message.isNotBlank()) {
                                    chatVm.sendMessage(defaultChatItem.copy(message = message))
                                    messageField = TextFieldValue()
                                } else {
                                    toast(getString(R.string.activity_chat_toast_input_message))
                                }
                            },
                        color = Blue,
                        shape = CircleShape
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_round_send_24),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ChatBubble(prevItem: ChatReceive?, item: ChatReceive, nextItem: ChatReceive?) {
        val date = item.createdAtStr.split(" ")[1].substringBeforeLast(":")
        val nextDate = nextItem?.createdAtStr?.split(" ")?.last()?.substringBeforeLast(":")
        val visibleTime = if (nextItem != null) {
            date != nextDate
        } else {
            true
        }
        val visibleProfileImage = if (prevItem != null) {
            item.userKey != prevItem.userKey
        } else {
            true
        }

        if (item.userKey != GameWaitingService.DeviceId) { // 왼쪽 (내꺼 아님)
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (profileImage, name, message, time) = createRefs()

                if (visibleProfileImage) {
                    Spacer(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = Color(ColorUtil.getColorForPosition(item.positionType)),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .constrainAs(profileImage) {
                                start.linkTo(parent.start)
                            }
                    )
                    Text(
                        text = item.positionName,
                        fontSize = 13.sp,
                        modifier = Modifier.constrainAs(name) {
                            start.linkTo(profileImage.start, 60.dp)
                        }
                    )
                    Surface(
                        modifier = Modifier.constrainAs(message) {
                            top.linkTo(name.bottom, 10.dp)
                            start.linkTo(name.start)
                        },
                        elevation = 1.dp,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = item.content,
                            color = Color.Black,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .requiredWidthIn(min = Dp.Unspecified, max = 200.dp)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                } else {
                    Surface(
                        modifier = Modifier.constrainAs(message) {
                            start.linkTo(parent.start, 60.dp)
                        },
                        elevation = 1.dp,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = item.content,
                            color = Color.Black,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .requiredWidthIn(min = Dp.Unspecified, max = 200.dp)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
                if (visibleTime) {
                    Text(
                        text = date,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.constrainAs(time) {
                            start.linkTo(message.start)
                            top.linkTo(message.bottom, 5.dp)
                        }
                    )
                }
            }
        } else { // 오른쪽 (내꺼임)
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (profileImage, name, message, time) = createRefs()

                if (visibleProfileImage) {
                    Spacer(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = Color(ColorUtil.getColorForPosition(item.positionType)),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .constrainAs(profileImage) {
                                end.linkTo(parent.end)
                            }
                    )
                    Text(
                        text = item.positionName,
                        fontSize = 13.sp,
                        modifier = Modifier.constrainAs(name) {
                            end.linkTo(parent.end, 60.dp)
                        }
                    )
                    Surface(
                        modifier = Modifier.constrainAs(message) {
                            top.linkTo(name.bottom, 10.dp)
                            end.linkTo(name.end)
                        },
                        elevation = 1.dp,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = item.content,
                            color = Color.Black,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .requiredWidthIn(min = Dp.Unspecified, max = 200.dp)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                } else {
                    Surface(
                        modifier = Modifier.constrainAs(message) {
                            end.linkTo(parent.end, 60.dp)
                        },
                        elevation = 1.dp,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = item.content,
                            color = Color.Black,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .requiredWidthIn(min = Dp.Unspecified, max = 200.dp)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
                if (visibleTime) {
                    Text(
                        text = date,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.constrainAs(time) {
                            start.linkTo(message.start)
                            top.linkTo(message.bottom, 5.dp)
                        }
                    )
                }
            }
        }
    }

    private fun startMapActivity(inviteCode: String, positionType: Int) {
        startActivityForResult(Intent(this, MapActivity::class.java).apply {
            putExtra(IntentConfig.ChatActivityInviteCode, inviteCode)
            putExtra(IntentConfig.ChatActivityPositionType, positionType)
            putExtra("wardOffsetX", chatVm.wardOffset.value.x)
            putExtra("wardOffsetY", chatVm.wardOffset.value.y)
            putExtra("warnOffsetX", chatVm.warnOffset.value.x)
            putExtra("warnOffsetY", chatVm.warnOffset.value.y)
        }, MAP_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                MAP_CODE -> {

                    if (data!!.getStringExtra("Ward") == "Ward") {
                        chatVm.sendMessage(
                            provideMapItem().copy(
                                message = "x :${
                                    data.getFloatExtra(
                                        "offsetX Ward",
                                        0.0f
                                    )
                                }, y:${data.getFloatExtra("offsetY Ward", 0.0f)}, Ward"
                            )
                        )
                    }
                    if (data!!.getStringExtra("Warn") == "Warn") {
                        chatVm.sendMessage(
                            provideMapItem().copy(
                                message = "x :${
                                    data.getFloatExtra(
                                        "offsetX Warn",
                                        0.0f
                                    )
                                }, y:${data.getFloatExtra("offsetY Warn", 0.0f)}, Warn"
                            )
                        )
                    }
                }
            }
        }
    }
}
