package team.mobileb.opgg.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import team.mobileb.opgg.R
import team.mobileb.opgg.activity.room.RoomActivity
import team.mobileb.opgg.activity.room.RoomState
import team.mobileb.opgg.theme.Blue
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.theme.transparentButtonElevation
import team.mobileb.opgg.ui.TriangleShape
import team.mobileb.opgg.util.config.IntentConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(Color.White)
        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val footerHeight = 70.dp
            val headerShape = RoundedCornerShape(15.dp)
            val (header, headerTail, image, footer) = createRefs()

            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .background(color = Blue, shape = headerShape)
                    .clip(headerShape)
                    .constrainAs(header) {
                        top.linkTo(parent.top, 50.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 30.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .clip(TriangleShape())
                    .background(Blue)
                    .constrainAs(headerTail) {
                        top.linkTo(header.bottom)
                        start.linkTo(header.start)
                        end.linkTo(header.end)
                    }
            )
            Image(
                painter = painterResource(R.drawable.ic_baseline_chats_250),
                contentDescription = null,
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(footer.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(footerHeight)
                    .constrainAs(footer) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Button(
                    onClick = { startRoomActivity(RoomState.Create) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = transparentButtonElevation()
                ) {
                    Text(
                        text = stringResource(R.string.activity_main_button_room_create),
                        color = Color.Black
                    )
                }
                Button(
                    onClick = { startRoomActivity(RoomState.Join) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Blue),
                    shape = RoundedCornerShape(topStart = 30.dp),
                    elevation = transparentButtonElevation()
                ) {
                    Text(
                        text = stringResource(R.string.activity_main_button_room_join),
                        color = Color.White
                    )
                }
            }
        }
    }

    private fun startRoomActivity(stateInitValue: Int) {
        startActivity(Intent(this, RoomActivity::class.java).apply {
            putExtra(IntentConfig.RoomActivityStateExtra, stateInitValue)
        })
    }
}
