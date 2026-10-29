package com.thoen.zaladaandroid.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FilledButtonCustom(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    @androidx.annotation.DrawableRes icon: Int? = null,
) {
    NeonButton(
        onClick = onClick,
        text = text,
        modifier = modifier.fillMaxWidth(),
        icon = icon,
    )
}
