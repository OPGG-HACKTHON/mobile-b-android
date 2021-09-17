package team.mobileb.opgg.activity.chat.map

import androidx.compose.ui.geometry.Offset

fun Offset.isVisible() = x != 0.0f && y != 0.0f
