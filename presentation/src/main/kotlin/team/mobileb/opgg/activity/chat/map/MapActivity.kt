package team.mobileb.opgg.activity.chat.map

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import team.mobileb.opgg.R
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.util.extension.toast
import kotlin.math.roundToInt

@AndroidEntryPoint
class MapActivity : ComponentActivity() {

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
        var warnOffset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
        var wardOffset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
        var markingType by remember { mutableStateOf<MarkingType>(MarkingType.Warn) }
        var isClicked by remember { mutableStateOf(false) }
        val updateWardOffset = Offset(
            intent.getFloatExtra("wardOffsetX", 0.0f),
            intent.getFloatExtra("wardOffsetY", 0.0f)
        )
        val updateWarnOffset = Offset(
            intent.getFloatExtra("warnOffsetX", 0.0f),
            intent.getFloatExtra("warnOffsetY", 0.0f)
        )

        if (updateWardOffset != Offset(0.0f, 0.0f) && !isClicked) {
            wardOffset = updateWardOffset
        }
        if (updateWarnOffset != Offset(0.0f, 0.0f) && !isClicked) {
            warnOffset = updateWarnOffset
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(350.dp)) {
                Image(
                    painter = painterResource(R.drawable.map),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = { offset ->
                                    isClicked = true
                                    if (markingType == MarkingType.Warn) {
                                        warnOffset = offset
                                    } else {
                                        wardOffset = offset
                                    }
                                }
                            )
                        }
                )
                if (warnOffset.isVisible()) {
                    MarkingIcon(type = MarkingType.Warn, offset = warnOffset)
                }
                if (wardOffset.isVisible()) {
                    MarkingIcon(type = MarkingType.Ward, offset = wardOffset)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { markingType = MarkingType.Warn }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_round_alert_25),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = stringResource(R.string.activity_map_button_warn),
                            color = Color.White
                        )
                    }
                    Button(onClick = { markingType = MarkingType.Ward }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_round_ward_20),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = stringResource(R.string.activity_map_button_ward),
                            color = Color.White
                        )
                    }
                }
                Button(
                    modifier = Modifier.padding(top = 30.dp),
                    onClick = {
                        if (warnOffset.isVisible()) {
                            intent.putExtra("offsetX Warn", warnOffset.x)
                            intent.putExtra("offsetY Warn", warnOffset.y)
                            intent.putExtra("Warn", MarkingType.Warn.value)
                        }
                        if (wardOffset.isVisible()) {
                            intent.putExtra("offsetX Ward", wardOffset.x)
                            intent.putExtra("offsetY Ward", wardOffset.y)
                            intent.putExtra("Ward", MarkingType.Ward.value)
                        }
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                        toast(getString(R.string.activity_map_toast_send_success))
                    },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_send_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.activity_map_button_send),
                        color = Color.White
                    )
                }
            }
        }
    }

    @Composable
    private fun MarkingIcon(type: MarkingType, offset: Offset) {
        Icon(
            modifier = Modifier
                .offset { IntOffset(x = offset.x.roundToInt(), y = offset.y.roundToInt()) }
                .size(20.dp),
            painter = painterResource(if (type == MarkingType.Warn) R.drawable.ic_round_alert_25 else R.drawable.ic_round_ward_20),
            contentDescription = null,
            tint = Color.White
        )
    }
}
