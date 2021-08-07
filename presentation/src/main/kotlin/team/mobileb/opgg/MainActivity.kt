package team.mobileb.opgg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
        Text(text = "Hello World!")
    }
}
