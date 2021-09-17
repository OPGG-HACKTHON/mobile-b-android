package team.mobileb.opgg.activity.room.composable

import android.content.Context
import android.content.Intent
import android.view.Window
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import team.mobileb.opgg.R
import team.mobileb.opgg.activity.chat.ChatActivity
import team.mobileb.opgg.activity.room.RoomError
import team.mobileb.opgg.activity.room.RoomViewModel
import team.mobileb.opgg.domain.doWhen
import team.mobileb.opgg.theme.Blue
import team.mobileb.opgg.theme.Gray
import team.mobileb.opgg.theme.LightGray
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.theme.transparentButtonElevation
import team.mobileb.opgg.util.config.IntentConfig
import team.mobileb.opgg.util.extension.toModel
import team.mobileb.opgg.util.extension.toast

@Composable
fun JoinRoom(window: Window) {
    SystemUiController(window).setStatusBarColor(Blue)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
    ) {
        Header(modifier = Modifier.weight(1f))
        Content(modifier = Modifier.weight(2f))
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
    val roomVm: RoomViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val positionsList = listOf("탑", "정글", "미드", "원딜", "서폿", null).chunked(2)
    val positionButtonShape = RoundedCornerShape(10.dp)
    val positionButtonHeight = 50.dp
    var selectedPosition by remember { mutableStateOf("") }

    @Composable
    fun positionButtonBackgroundColor(position: String) =
        animateColorAsState(if (selectedPosition == position) Blue else Gray).value

    @Composable
    fun positionButtonTextColor(position: String) =
        animateColorAsState(if (selectedPosition == position) Color.White else Color.Black).value

    var linkField by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(RoomContentShape)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
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
                .fillMaxWidth()
                .focusRequester(FocusRequester()),
            singleLine = true,
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            }
        )
        Text(
            text = stringResource(R.string.composable_join_position),
            color = Color.Black,
            fontSize = 18.sp
        )
        positionsList.forEach { positions ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                positions.forEach { position ->
                    if (position != null) {
                        Button(
                            onClick = { selectedPosition = position },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = positionButtonBackgroundColor(position)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(positionButtonHeight),
                            shape = positionButtonShape
                        ) {
                            Text(text = position, color = positionButtonTextColor(position))
                        }
                    } else {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .height(positionButtonHeight)
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.composable_join_label),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Button(
                onClick = {
                    val link = linkField.text
                    if (link.isNotBlank()) {
                        if (selectedPosition != "") {
                            coroutineScope.launch {
                                roomVm.checkRoom(link).collect { checkResult ->
                                    checkResult.doWhen(
                                        onSuccess = { check ->
                                            if (check.code == 200) {
                                                startChatActivity(
                                                    context = context,
                                                    inviteCode = link,
                                                    positionType = positionsList.flatten()
                                                        .indexOf(selectedPosition)
                                                )
                                            } else {
                                                toast(
                                                    context,
                                                    context.getString(
                                                        R.string.composable_room_toast_error,
                                                        check.message.toModel<RoomError>().message
                                                    )
                                                )
                                            }
                                        },
                                        onFail = {
                                            toast(
                                                context,
                                                context.getString(R.string.composable_room_toast_confirm_code)
                                            )
                                        }
                                    )
                                }
                            }
                        } else {
                            toast(
                                context,
                                context.getString(R.string.composable_join_toast_confirm_position)
                            )
                        }
                    } else {
                        toast(
                            context,
                            context.getString(R.string.composable_room_toast_insert_link)
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Blue),
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

private fun startChatActivity(context: Context, inviteCode: String, positionType: Int) {
    context.startActivity(Intent(context, ChatActivity::class.java).apply {
        putExtra(IntentConfig.ChatActivityInviteCode, inviteCode)
        putExtra(IntentConfig.ChatActivityPositionType, positionType)
    })
}
