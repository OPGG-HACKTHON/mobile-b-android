package team.mobileb.opgg.theme

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = MaterialColorPalette, content = { content() })
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
