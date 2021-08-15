package team.mobileb.opgg.activity.room

import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import team.mobileb.opgg.R
import team.mobileb.opgg.theme.LightGray
import team.mobileb.opgg.theme.Orange
import team.mobileb.opgg.theme.SystemUiController

@Composable
fun CreateRoom(window: Window, onStateChangeAction: () -> Unit) {
    SystemUiController(window).setStatusBarColor(Orange)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange)
    ) {
        Header(modifier = Modifier.weight(1f))
        Content(modifier = Modifier.weight(1f), onStateChangeAction = onStateChangeAction)
    }
}

@Composable
private fun Header(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateHeaderContent()
    }
}

@Composable
private fun Content(modifier: Modifier, onStateChangeAction: () -> Unit) {
    var linkField by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 30.dp))
            .padding(30.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.activity_room_link), color = Color.Black)
            TextField(
                value = linkField,
                onValueChange = { linkField = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = LightGray,
                    disabledIndicatorColor = LightGray,
                    focusedIndicatorColor = LightGray,
                    unfocusedIndicatorColor = LightGray
                ),
                shape = RoundedCornerShape(15.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.activiry_room_create),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.clickable { onStateChangeAction() }
            )
            FloatingActionButton(onClick = {}, backgroundColor = Orange) { // todo: onClick Action
                Icon(
                    painter = painterResource(R.drawable.ic_round_arrow_forward_24),
                    contentDescription = null
                )
            }
        }
    }
}
