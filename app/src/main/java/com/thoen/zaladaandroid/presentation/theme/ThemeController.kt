package com.thoen.zaladaandroid.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.thoen.zaladaandroid.domain.model.ThemeMode

data class ThemeController(
    val mode: ThemeMode,
    val onModeChange: (ThemeMode) -> Unit,
)

val LocalThemeController = staticCompositionLocalOf<ThemeController?> { null }

@Composable
fun ThemeMode.resolveDark(): Boolean = when (this) {
    ThemeMode.System -> isSystemInDarkTheme()
    ThemeMode.Light -> false
    ThemeMode.Dark -> true
}

fun ThemeMode.label(): String = when (this) {
    ThemeMode.System -> "System default"
    ThemeMode.Light -> "Light"
    ThemeMode.Dark -> "Dark"
}
