package com.thoen.zaladaandroid.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Frosted circular icon button used in app bars. */
@Composable
fun ShapeIcons(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    size: Int = 44,
) {
    GlassIconButton(
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        size = size.dp,
    )
}
