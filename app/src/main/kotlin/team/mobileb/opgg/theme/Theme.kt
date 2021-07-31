package team.mobileb.opgg.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*val colors = lightColors().copy(
    primary = Color(0xFF6b1aa5),
    primaryVariant = Color(0xFF380075),
    secondary = Color(0xFF9e4dd7)
)*/

@Composable
fun MaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme(/*todo*/) {
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
