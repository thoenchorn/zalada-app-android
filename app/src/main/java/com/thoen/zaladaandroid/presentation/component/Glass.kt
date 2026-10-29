package com.thoen.zaladaandroid.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoen.zaladaandroid.presentation.theme.NeonCyan
import com.thoen.zaladaandroid.presentation.theme.NeonViolet
import com.thoen.zaladaandroid.presentation.theme.NeonButtonGradientHorizontal
import com.thoen.zaladaandroid.presentation.theme.appBackground
import com.thoen.zaladaandroid.presentation.theme.glassBorder
import com.thoen.zaladaandroid.presentation.theme.glassCardBrush
import com.thoen.zaladaandroid.presentation.theme.glassFill
import com.thoen.zaladaandroid.presentation.theme.isNight

/**
 * Root canvas: deep navy gradient with soft violet/cyan light blooms.
 * Kept transparent-friendly so Scaffolds can opt out of their own background.
 */
@Composable
fun AppBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val night = isNight()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(appBackground())
    ) {
        Box(
            modifier = Modifier
                .size(460.dp)
                .offset(x = (-140).dp, y = (-120).dp)
                .background(
                    Brush.radialGradient(
                        listOf(
                            NeonViolet.copy(alpha = if (night) 0.30f else 0.12f),
                            NeonViolet.copy(alpha = if (night) 0.14f else 0.06f),
                            NeonViolet.copy(alpha = 0f)
                        )
                    ),
                    CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(520.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 180.dp, y = 220.dp)
                .background(
                    Brush.radialGradient(
                        listOf(
                            NeonCyan.copy(alpha = if (night) 0.20f else 0.10f),
                            NeonCyan.copy(alpha = if (night) 0.08f else 0.04f),
                            NeonCyan.copy(alpha = 0f)
                        )
                    ),
                    CircleShape
                )
        )
        content()
    }
}

/** Translucent surface with a hairline border and optional neon glow. */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    glow: Boolean = false,
    bordered: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val accent = MaterialTheme.colorScheme.primary
    val border = if (glow) {
        Brush.linearGradient(
            listOf(
                accent.copy(alpha = 0.65f),
                glassBorder(),
                accent.copy(alpha = 0.25f),
            )
        )
    } else {
        Brush.linearGradient(listOf(glassBorder(), glassBorder()))
    }

    Box(
        modifier = modifier
            .then(
                if (glow) {
                    Modifier.shadow(
                        elevation = 22.dp,
                        shape = shape,
                        ambientColor = accent.copy(alpha = 0.8f),
                        spotColor = accent.copy(alpha = 0.8f),
                        clip = false,
                    )
                } else {
                    Modifier
                }
            )
            .clip(shape)
            .background(glassCardBrush())
            .then(
                if (bordered) {
                    Modifier.border(width = 1.dp, brush = border, shape = shape)
                } else {
                    Modifier
                }
            )
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        content = content
    )
}

/** Gradient pill button with a neon glow, used for every primary action. */
@Composable
fun NeonButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
) {
    val shape = CircleShape
    val accent = MaterialTheme.colorScheme.primary
    Row(
        modifier = modifier
            .heightIn(min = 54.dp)
            .shadow(
                elevation = if (enabled) 18.dp else 0.dp,
                shape = shape,
                ambientColor = accent.copy(alpha = 0.9f),
                spotColor = accent.copy(alpha = 0.9f),
                clip = false,
            )
            .clip(shape)
            .background(if (enabled) NeonButtonGradientHorizontal else Brush.linearGradient(listOf(accent.copy(alpha = 0.4f), accent.copy(alpha = 0.4f))))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 26.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (icon != null) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )
    }
}

/** Circular frosted-glass icon button used in every app bar / hero. */
@Composable
fun GlassIconButton(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    badgeCount: Int = 0,
    glow: Boolean = false,
    tint: Color = MaterialTheme.colorScheme.onSurface,
) {
    val accent = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .then(
                if (glow) {
                    Modifier.shadow(
                        elevation = 14.dp,
                        shape = CircleShape,
                        ambientColor = accent.copy(alpha = 0.7f),
                        spotColor = accent.copy(alpha = 0.7f),
                        clip = false,
                    )
                } else {
                    Modifier
                }
            )
            .size(size)
            .clip(CircleShape)
            .background(glassFill())
            .border(1.dp, glassBorder(), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(tint),
            modifier = Modifier.size(size * 0.46f)
        )
        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(19.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Color(0xFF2E6BFF), Color(0xFF22C3F0)))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (badgeCount > 9) "9+" else badgeCount.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

/** Divider hairline that reads correctly on glass. */
@Composable
fun glassHairline(): BorderStroke = BorderStroke(1.dp, glassBorder())

@Composable
fun glassPanelShape(): RoundedCornerShape = RoundedCornerShape(24.dp)
