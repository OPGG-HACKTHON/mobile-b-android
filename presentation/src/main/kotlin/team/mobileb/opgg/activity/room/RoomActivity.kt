package team.mobileb.opgg.activity.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.util.config.IntentConfig

class RoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setNavigationBarColor(Color.White)
        setContent {
            MaterialTheme {
                Content(
                    intent.getIntExtra(
                        IntentConfig.RoomActivityInitStateExtra,
                        RoomState.Create
                    )
                )
            }
        }
    }

    @Composable
    private fun Content(state: Int) {
        when (state) {
            RoomState.Join -> {
                JoinRoom(window = window, buttonAction = { /* todo */ })
            }
            RoomState.Create -> {
                CreateRoom(window = window, buttonAction = { /* todo */ })
            }
        }
    }
}
