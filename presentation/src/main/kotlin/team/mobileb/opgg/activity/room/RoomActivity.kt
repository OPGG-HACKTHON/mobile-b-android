package team.mobileb.opgg.activity.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import team.mobileb.opgg.activity.room.composable.CreateRoom
import team.mobileb.opgg.activity.room.composable.JoinRoom
import team.mobileb.opgg.theme.MaterialTheme
import team.mobileb.opgg.theme.SystemUiController
import team.mobileb.opgg.util.config.IntentConfig

@AndroidEntryPoint
class RoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setNavigationBarColor(Color.White)
        setContent {
            MaterialTheme {
                Content(intent.getIntExtra(IntentConfig.RoomActivityState, RoomPage.Create))
            }
        }
    }

    @Composable
    private fun Content(page: Int) {
        when (page) {
            RoomPage.Join -> {
                JoinRoom(window = window)
            }
            RoomPage.Create -> {
                CreateRoom(window = window)
            }
        }
    }
}
