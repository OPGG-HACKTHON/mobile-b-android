package team.mobileb.opgg.activity.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import team.mobileb.opgg.R
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.util.config.IntentConfig
import team.mobileb.opgg.util.extension.toast

class RoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setNavigationBarColor(Color.White)
        setContent {
            MaterialTheme {
                Content(intent.getIntExtra(IntentConfig.RoomActivityState, RoomState.Create))
            }
        }
    }

    @Composable
    private fun Content(state: Int) {
        when (state) {
            RoomState.Join -> {
                JoinRoom(
                    window = window,
                    buttonAction = { link, position ->
                        if (link.isEmpty()) {
                            toast(getString(R.string.activity_room_toast_insert_link))
                        } else {
                            // todo
                        }
                    })
            }
            RoomState.Create -> {
                CreateRoom(
                    window = window,
                    buttonAction = { link ->
                        if (link.isEmpty()) {
                            toast(getString(R.string.activity_room_toast_insert_link))
                        } else {
                            // todo
                        }
                    })
            }
        }
    }
}
