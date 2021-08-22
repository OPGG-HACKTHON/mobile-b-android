package team.mobileb.opgg.activity.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import team.mobileb.opgg.R
import team.mobileb.opgg.theme.Blue
import team.mobileb.opgg.theme.ChatColor
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.Pink
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.theme.transparentTextFieldColors
import team.mobileb.opgg.util.StringUtil
import team.mobileb.opgg.util.config.IntentConfig
import kotlin.random.Random

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                            intent.getStringExtra(IntentConfig.ChatActivityRoomName) ?: "TestRoom"
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
        var messageField by remember { mutableStateOf(TextFieldValue()) }

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
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(items = List(50) { StringUtil.getRandom(Random.nextInt(50)) }) { randomMessage ->
                    if (Random.nextBoolean()) { // test case
                        OwnBubble(message = randomMessage)
                    } else {
                        OtherBubble(message = randomMessage)
                    }
                }
            }
            FloatingActionButton(
                modifier = Modifier.constrainAs(fab) {
                    end.linkTo(parent.end, defaultPadding)
                    bottom.linkTo(textField.top, messageFieldTopPadding)
                },
                onClick = { /*TODO*/ },
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
                            .clickable { /* todo */ },
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

    @Composable
    private fun OwnBubble(message: String) { // 내 채팅 버블
        val defaultHeight = 50.dp
        val shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp)

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (bubble, profileImage) = createRefs()

            Text(
                modifier = Modifier
                    .heightIn(min = defaultHeight)
                    .clip(shape)
                    .background(color = ChatColor.OwnBubbleBackgroundColor, shape = shape)
                    .constrainAs(bubble) {
                        start.linkTo(parent.start)
                        end.linkTo(profileImage.start, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(15.dp),
                text = message,
                color = Color.White
            )
            Spacer(
                modifier = Modifier
                    .size(defaultHeight)
                    .background(ChatColor.ProfileImageBackgroundColor, CircleShape)
                    .clip(CircleShape)
                    .constrainAs(profileImage) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }

    @Composable
    private fun OtherBubble(message: String) { // 상대 채팅 버블
        val defaultHeight = 50.dp
        val shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomEnd = 15.dp)

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (profileImage, bubble) = createRefs()

            Spacer(
                modifier = Modifier
                    .size(defaultHeight)
                    .background(ChatColor.ProfileImageBackgroundColor, CircleShape)
                    .clip(CircleShape)
                    .constrainAs(profileImage) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Text(
                modifier = Modifier
                    .heightIn(min = defaultHeight)
                    .clip(shape)
                    .background(color = ChatColor.OtherBubbleBackgroundColor, shape = shape)
                    .constrainAs(bubble) {
                        start.linkTo(profileImage.end, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(15.dp),
                text = message,
                color = Color.Black
            )
        }
    }
}
