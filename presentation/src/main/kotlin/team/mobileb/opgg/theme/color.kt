package team.mobileb.opgg.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Pink = Color(0xFFf94c83)
val Blue = Color(0xFF587de5)
val LightGray = Color(0xFFebebeb)
val Gray = Color(0xFFcccccc)
val HalfTransparentRed = Color(0x80FF0000)

val MaterialColorPalette = lightColors().copy(
    primary = Blue,
    primaryVariant = Color(0xFF0b51b2),
    secondary = Color(0xFF8facff)
)

object ChatColor {
    val OtherBubbleBackgroundColor = Color(0xFFedf1f4) // 상대 채팅버블 배경 색
    val OwnBubbleBackgroundColor = Blue // 내 채팅버블 배경 색
    val TextFieldBackgroundColor = Color(0xFFf8f8f8)
}
