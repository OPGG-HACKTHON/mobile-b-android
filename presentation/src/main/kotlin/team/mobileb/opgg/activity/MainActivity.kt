package team.mobileb.opgg.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import team.mobileb.opgg.theme.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Main()
            }
        }
    }

    @Composable
    private fun Main() {
        val text = remember { mutableStateOf("1") }
        Text(text = text.value)
    }
}
