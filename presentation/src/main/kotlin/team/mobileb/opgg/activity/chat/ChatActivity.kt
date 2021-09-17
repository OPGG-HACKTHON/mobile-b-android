package team.mobileb.opgg.activity.chat

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import team.mobileb.opgg.GameWaitingService
import team.mobileb.opgg.R
import team.mobileb.opgg.activity.chat.map.MapActivity
import team.mobileb.opgg.activity.chat.model.ChatReceive
import team.mobileb.opgg.activity.chat.util.provideChatItem
import team.mobileb.opgg.activity.chat.util.provideMapItem
import team.mobileb.opgg.theme.Blue
import team.mobileb.opgg.theme.ChatColor
import team.mobileb.opgg.theme.HalfTransparentRed
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.theme.transparentTextFieldColors
import team.mobileb.opgg.ui.multifab.MultiFabItem
import team.mobileb.opgg.ui.multifab.MultiFloatingActionButton
import team.mobileb.opgg.util.ColorUtil
import team.mobileb.opgg.util.NotificationUtil
import team.mobileb.opgg.util.config.IntentConfig
import team.mobileb.opgg.util.config.RtdbConfig
import team.mobileb.opgg.util.extension.toModel
import team.mobileb.opgg.util.extension.toast

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {
    private val MAP_CODE = 20
    private val chatVm: ChatViewModel by viewModels()
    private var gameStart by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseDatabase.getInstance().getReference(provideChatItem().inviteCode)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.getValue(String::class.java) == RtdbConfig.Notice) {
                        FirebaseDatabase.getInstance().getReference(provideChatItem().inviteCode)
                            .removeValue()
                        NotificationUtil.noticeGameStart(applicationContext)
                        gameStart = true
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            })

        chatVm.connectSocket(provideChatItem())
        setContent {
            MaterialTheme {
                GameStartContentCover {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Toolbar(
                                roomName = intent.getStringExtra(IntentConfig.ChatActivityInviteCode)!!
                            )
                        },
                        content = {
                            Content()
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun GameStartContentCover(content: @Composable () -> Unit) {
        if (gameStart) {
            SystemUiController(window).setSystemBarsColor(HalfTransparentRed)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = HalfTransparentRed),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.notification_gamestart_title),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_alarm_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(vertical = 50.dp),
                    tint = Color.White
                )
                Button(
                    onClick = { gameStart = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = RoundedCornerShape(15.dp),
                    contentPadding = PaddingValues(horizontal = 30.dp, vertical = 15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.notification_gamestart_content),
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                }
            }
        } else {
            SystemUiController(window).run {
                setStatusBarColor(Blue)
                setNavigationBarColor(Color.White)
            }
            content()
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
                .collect { _message ->
                    val message: ChatReceive = _message.toModel()
                    if (message.content.contains("Warn")) {
                        val list = message.content.split(",")
                        val x = list[0].split(":")[1].toFloat()
                        val y = list[1].split(":")[1].toFloat()
                        intent.putExtra("warnOffsetX", list[0].split(":")[1].toFloat())
                        intent.putExtra("warnOffsetY", list[1].split(":")[1].toFloat())
                        chatVm.warnOffset.value = Offset(x, y)
                        message.content = "지도에 위험 핑을 업데이트 했습니다"
                    }
                    if (message.content.contains("Ward")) {
                        val list = message.content.split(",")
                        val x = list[0].split(":")[1].toFloat()
                        val y = list[1].split(":")[1].toFloat()
                        intent.putExtra("warnOffsetX", list[0].split(":")[1].toFloat())
                        intent.putExtra("warnOffsetY", list[1].split(":")[1].toFloat())
                        chatVm.wardOffset.value = Offset(x, y)
                        message.content = "지도에 와드 핑을 업데이트 했습니다"
                    }
                    messages.add(message)
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
            MultiFloatingActionButton(
                modifier = Modifier.constrainAs(fab) {
                    end.linkTo(parent.end, defaultPadding)
                    bottom.linkTo(textField.top, messageFieldTopPadding)
                },
                fabIcon = painterResource(R.drawable.ic_round_add_24),
                fabIconRotate = true,
                items = listOf(
                    MultiFabItem(
                        id = 0,
                        label = "지도",
                        icon = painterResource(R.drawable.ic_round_map_24)
                    ),
                    MultiFabItem(
                        id = 1,
                        label = "게임 시작",
                        icon = painterResource(R.drawable.ic_round_alert_25)
                    )
                ),
                onFabItemClicked = { item ->
                    when (item.id) {
                        0 -> { // 지도
                            startMapActivity(
                                defaultChatItem.inviteCode,
                                defaultChatItem.positionType
                            )
                        }
                        1 -> { // 게임 시작
                            FirebaseDatabase.getInstance()
                                .getReference(defaultChatItem.inviteCode)
                                .push()
                                .setValue(RtdbConfig.Notice)
                        }
                    }
                }
            )
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
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = Color(ColorUtil.getColorForPosition(item.positionType)),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .constrainAs(profileImage) {
                                start.linkTo(parent.start)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(item.getPositionIconRes()),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = Color.Unspecified
                        )
                    }
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
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                color = Color(ColorUtil.getColorForPosition(item.positionType)),
                                shape = CircleShape
                            )
                            .constrainAs(profileImage) {
                                end.linkTo(parent.end)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(item.getPositionIconRes()),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = Color.Unspecified
                        )
                    }
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
                    if (data.getStringExtra("Warn") == "Warn") {
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
