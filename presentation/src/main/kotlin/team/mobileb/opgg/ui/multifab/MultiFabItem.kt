package team.mobileb.opgg.ui.multifab

import androidx.compose.ui.graphics.painter.Painter

data class MultiFabItem(
    val id: Int,
    val icon: Painter,
    val label: String = ""
)
