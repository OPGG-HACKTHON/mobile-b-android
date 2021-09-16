package team.mobileb.opgg.activity.map

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dagger.hilt.android.AndroidEntryPoint
import team.mobileb.opgg.GameWaitingService
import team.mobileb.opgg.R
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.SystemUiController
import kotlin.math.roundToInt

@AndroidEntryPoint
class MapActivity : ComponentActivity() {

    private val mapVm: MapViewModel by viewModels()
    private var isEnabledWarning = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(Color.White)
        setContent {
            MaterialTheme {
                Screen()
            }
        }
    }

    @Composable
    fun Screen() {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }
        var inviteCode = "test0912"
        //intent.getStringExtra(IntentConfig.ChatActivityInviteCode)!!
        var positionType = 1
        //intent.getIntExtra(IntentConfig.ChatActivityPositionType, 0)
        var userKey = GameWaitingService.DeviceId
        Column() {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.map), contentDescription = null,
                    modifier = Modifier
                        .size(350.dp, 350.dp)
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = { offset ->
                                    mapVm.offset.value = offset
                                    if (inviteCode != null) {
                                        mapVm.sendWarningPin(
                                            userKey,
                                            inviteCode,
                                            positionType
                                        )
                                    }
                                }
                            )
                        }
                )
                if(isEnabledWarning.value) {
                    WarningIcon(mapVm.offset.value)
                }

            }
            Share()
            Buttons()
        }


    }

    @Composable
    fun WarningIcon(offset: Offset) {

        Icon(
            modifier = Modifier.offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }.size(30.dp),
            painter = painterResource(id = R.drawable.warning_pin),
            contentDescription = null
        )
        Log.i("Warning", "${offset.x} , ${offset.y}")
    }

    @Composable
    fun Share() {
        Row {
            Text("톡방에 공유하기")
            Icon(painter = painterResource(id = R.drawable.ic_round_send_24),
                contentDescription = null,
                modifier = Modifier.clickable {
                    // TODO share
                })
        }
    }

    @Composable
    fun Buttons() {
        Button(onClick = { isEnabledWarning.value = true }) {
            ConstraintLayout() {
                val (icon, text) = createRefs()
                Row() {
                    Icon(
                        modifier = Modifier.padding(end = 16.dp),
                        painter = painterResource(id = R.drawable.ic_alert_circle),
                        contentDescription = null
                    )
                    Text(text = "위험")
                }

            }


        }
    }

}
