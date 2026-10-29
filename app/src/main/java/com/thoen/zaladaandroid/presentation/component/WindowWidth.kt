package com.thoen.zaladaandroid.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowWidth {
    Compact,
    Medium,
    Expanded,
}

/**
 * Material 3 breakpoints. Foldables report their unfolded width through the configuration,
 * so the same breakpoints drive phone, fold and tablet layouts.
 */
@Composable
fun rememberWindowWidth(): WindowWidth {
    val widthDp = LocalConfiguration.current.screenWidthDp
    return when {
        widthDp < 600 -> WindowWidth.Compact
        widthDp < 840 -> WindowWidth.Medium
        else -> WindowWidth.Expanded
    }
}

val WindowWidth.horizontalPadding: Dp
    get() = when (this) {
        WindowWidth.Compact -> 20.dp
        WindowWidth.Medium -> 28.dp
        WindowWidth.Expanded -> 40.dp
    }

val WindowWidth.contentMaxWidth: Dp
    get() = when (this) {
        WindowWidth.Compact -> Dp.Unspecified
        WindowWidth.Medium -> 760.dp
        WindowWidth.Expanded -> 1120.dp
    }

val WindowWidth.productCardMinWidth: Dp
    get() = when (this) {
        WindowWidth.Compact -> 150.dp
        WindowWidth.Medium -> 170.dp
        WindowWidth.Expanded -> 200.dp
    }

val WindowWidth.isExpanded: Boolean
    get() = this == WindowWidth.Expanded

val WindowWidth.isCompact: Boolean
    get() = this == WindowWidth.Compact
