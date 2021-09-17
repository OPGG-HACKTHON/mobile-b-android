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
import team.mobileb.opgg.util.config.IntentConfig
import kotlin.math.roundToInt

@AndroidEntryPoint
class MapActivity : ComponentActivity() {

    private val mapVm: MapViewModel by viewModels()
    private var isEnabledWarning = mutableStateOf(false)
    private var isEnabledWard = mutableStateOf(false)
    private var isWardClicked = mutableStateOf(false)
    private var isWarnClicked = mutableStateOf(false)
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
                                    if (isEnabledWarning.value) {
                                        mapVm.warnOffset.value = offset
                                        isWarnClicked.value = true
                                    } else {
                                        mapVm.wardOffset.value = offset
                                        isWardClicked.value = true
                                    }
                                }
                            )
                        }
                )
                if(isWarnClicked.value) {
                    WarningIcon(offset = mapVm.warnOffset.value)
                }
                if(isWardClicked.value) {
                    WardIcon(offset = mapVm.wardOffset.value)
                }
            }
            Share()
            Buttons()
        }


    }

    @Composable
    fun WarningIcon(offset: Offset) {

        Icon(
            modifier = Modifier
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .size(20.dp),
            painter = painterResource(id = R.drawable.ic_alert_circle),

            contentDescription = null
        )
        Log.i("Warning", "${offset.x} , ${offset.y}")
    }

    @Composable
    fun WardIcon(offset: Offset) {
        Icon(
            modifier = Modifier
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .size(20.dp),
            painter = painterResource(id = R.drawable.ic_ward_pin),
            contentDescription = null
        )
    }

    @Composable
    fun Share() {
        // TODO 사용자 값 받기
        var inviteCode = "test20"
        var positionType = 1
        var userKey = GameWaitingService.DeviceId
        Row {
            Text("톡방에 공유하기")
            Icon(painter = painterResource(id = R.drawable.ic_round_send_24),
                contentDescription = null,
                modifier = Modifier.clickable {

                        mapVm.sendWarningPin(
                            userKey,
                            inviteCode,
                            positionType
                        )
                        mapVm.sendWardPin(userKey, inviteCode, positionType)
                        finish()
                })
        }
    }

    @Composable
    fun Buttons() {

        Row() {
            Button(onClick = { warnEnableCheck() }) {
                Row() {
                    Icon(
                        modifier = Modifier.padding(start = 18.dp, top = 12.dp, bottom = 12.dp, end = 18.dp),
                        painter = painterResource(id = R.drawable.ic_alert_circle),
                        contentDescription = null
                    )
                    Text(modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 18.dp), text = "위험")
                }
            }
            Button(onClick = { wardEnableCheck() }) {
                Row() {
                    Icon(
                        modifier = Modifier.padding(start = 18.dp, top = 12.dp, bottom = 12.dp, end = 18.dp),
                        painter = painterResource(id = R.drawable.ic_ward_pin),
                        contentDescription = null
                    )
                    Text(modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 18.dp),text = "와드")
                }
            }
        }
    }
    private fun warnEnableCheck() {
        if (isEnabledWard.value) {
            isEnabledWard.value = false
        }
        isEnabledWarning.value = true
    }

    private fun wardEnableCheck() {
        if (isEnabledWarning.value) {
            isEnabledWarning.value = false
        }
        isEnabledWard.value = true
    }
}
