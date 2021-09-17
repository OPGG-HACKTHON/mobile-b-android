package team.mobileb.opgg.ui.multifab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

sealed class MultiFabState {
    object Collapsed : MultiFabState()
    object Expaned : MultiFabState()

    fun toggle() = if (this == Expaned) {
        Collapsed
    } else {
        Expaned
    }
}

@Composable
fun rememberMultiFabState() = remember { mutableStateOf<MultiFabState>(MultiFabState.Collapsed) }
