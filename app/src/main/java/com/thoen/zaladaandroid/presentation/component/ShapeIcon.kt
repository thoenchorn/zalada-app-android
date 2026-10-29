package com.thoen.zaladaandroid.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Frosted circular action button used on top of the hero (cart / search). */
@Composable
fun ShapeIcon(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    count: Int = 0,
    modifier: Modifier = Modifier,
) {
    GlassIconButton(
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        size = 48.dp,
        badgeCount = count,
        glow = true,
    )
}
