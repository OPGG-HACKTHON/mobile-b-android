package team.mobileb.opgg.activity.room

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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import team.mobileb.opgg.R
import team.mobileb.opgg.theme.LightGray
import team.mobileb.opgg.theme.Pink
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.theme.transparentButtonElevation

@Composable
fun CreateRoom(window: Window, buttonAction: (linkAddress: String) -> Unit) {
    SystemUiController(window).setStatusBarColor(Pink)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink)
    ) {
        Header(modifier = Modifier.weight(1f))
        Content(modifier = Modifier.weight(1f), onStateChangeAction = buttonAction)
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
private fun Content(modifier: Modifier, onStateChangeAction: (linkAddress: String) -> Unit) {
    var linkField by remember { mutableStateOf(TextFieldValue()) }

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
                text = stringResource(R.string.activity_room_label_link),
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
                singleLine = true
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
                text = stringResource(R.string.activity_room_label_create),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Button(
                onClick = { onStateChangeAction(linkField.text) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Pink),
                shape = CircleShape,
                modifier = Modifier.size(50.dp),
                elevation = transparentButtonElevation()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_arrow_forward_24),
                    contentDescription = null
                )
            }
        }
    }
}
