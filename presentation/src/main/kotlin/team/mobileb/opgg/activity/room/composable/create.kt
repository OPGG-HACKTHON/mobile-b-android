package team.mobileb.opgg.activity.room.composable

import android.content.Context
import android.content.Intent
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import team.mobileb.opgg.GameWaitingService
import team.mobileb.opgg.R
import team.mobileb.opgg.activity.chat.ChatActivity
import team.mobileb.opgg.activity.room.RoomError
import team.mobileb.opgg.activity.room.RoomViewModel
import team.mobileb.opgg.domain.doWhen
import team.mobileb.opgg.domain.model.CreateRoomData
import team.mobileb.opgg.theme.LightGray
import team.mobileb.opgg.theme.Pink
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.theme.transparentButtonElevation
import team.mobileb.opgg.util.config.IntentConfig
import team.mobileb.opgg.util.config.RtdbConfig
import team.mobileb.opgg.util.extension.toModel
import team.mobileb.opgg.util.extension.toast

@Composable
fun CreateRoom(window: Window) {
    SystemUiController(window).setStatusBarColor(Pink)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink)
    ) {
        Header(modifier = Modifier.weight(1f))
        Content(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun Header(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateHeaderContent()
    }
}

@Composable
private fun Content(modifier: Modifier) {
    val context = LocalContext.current
    val roomVm: RoomViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    var linkField by remember { mutableStateOf(TextFieldValue()) }

    fun createRoom() {
        val link = linkField.text
        coroutineScope.launch {
            roomVm.createRoom(
                CreateRoomData(userKey = GameWaitingService.DeviceId, inviteCode = link)
            ).collect { createResult ->
                createResult.doWhen(
                    onSuccess = {
                        FirebaseDatabase.getInstance().getReference(link).push()
                            .setValue(RtdbConfig.CreateRoom)
                        startChatActivity(context = context, inviteCode = link)
                    },
                    onFail = { exception ->
                        toast(
                            context,
                            context.getString(
                                R.string.composable_room_toast_error,
                                exception.message!!.toModel<RoomError>().message
                            )
                        )
                    }
                )
            }
        }
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .clip(RoomContentShape)
            .background(Color.White)
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val (content, footer) = createRefs()

        Column(
            modifier = Modifier.constrainAs(content) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(footer.top, 15.dp)
                width = Dimension.fillToConstraints
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.composable_room_link),
                color = Color.Black,
                fontSize = 18.sp
            )
            TextField(
                value = linkField,
                onValueChange = { linkField = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = LightGray,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions {
                    createRoom()
                }
            )
        }
        Row(
            modifier = Modifier.constrainAs(footer) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.composable_create_label),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Button(
                onClick = {
                    createRoom()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Pink),
                shape = CircleShape,
                modifier = Modifier.size(50.dp),
                elevation = transparentButtonElevation()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_arrow_forward_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

private fun startChatActivity(context: Context, inviteCode: String) {
    context.startActivity(Intent(context, ChatActivity::class.java).apply {
        putExtra(IntentConfig.ChatActivityInviteCode, inviteCode)
    })
}
