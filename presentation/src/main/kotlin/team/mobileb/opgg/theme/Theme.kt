package team.mobileb.opgg.theme

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// todo: setup main color
/*val colors = lightColors().copy(
    primary = Color(0xFF6b1aa5),
    primaryVariant = Color(0xFF380075),
    secondary = Color(0xFF9e4dd7)
)*/

val Pink = Color(0xFFf94c83)
val Blue = Color(0xFF0b70fe)
val LightBlue = Color(0xFF587de5)
val LightGray = Color(0xFFebebeb)
val Gray = Color(0xFFcccccc)

@Composable
fun MaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}

@Composable
fun transparentTextFieldColors(
    backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity),
    textColor: Color = Color.Black
) = TextFieldDefaults.textFieldColors(
    disabledIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    backgroundColor = backgroundColor,
    textColor = textColor
)

@Composable
fun transparentButtonElevation() = ButtonDefaults.elevation(
    defaultElevation = 0.dp,
    pressedElevation = 0.dp
)
