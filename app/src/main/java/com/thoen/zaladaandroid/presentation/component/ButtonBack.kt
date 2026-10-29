package com.thoen.zaladaandroid.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thoen.zaladaandroid.R

@Composable
fun ButtonBack(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    GlassIconButton(
        onClick = onClick,
        icon = R.drawable.arrow_left,
        modifier = modifier,
    )
}
