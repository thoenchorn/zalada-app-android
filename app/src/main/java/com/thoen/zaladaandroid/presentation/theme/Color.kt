package com.thoen.zaladaandroid.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.Color

// ---------------------------------------------------------------------------
// Neon glass palette (2026 reference): deep navy canvas, electric blue accents
// and translucent glass surfaces with glowing edges.
// ---------------------------------------------------------------------------

val NeonBlue = Color(0xFF3B82F6)
val NeonBlueBright = Color(0xFF60A5FA)
val NeonCyan = Color(0xFF22D3EE)
val NeonViolet = Color(0xFF8B5CF6)
val NeonIndigo = Color(0xFF4F46E5)

val NavyDeep = Color(0xFF050B1A)
val Navy = Color(0xFF081428)
val NavySoft = Color(0xFF0D1F3C)

val GlassFillDark = Color(0x1FFFFFFF)
val GlassFillStrongDark = Color(0x2EFFFFFF)
val GlassBorderDark = Color(0x2EFFFFFF)
val GlassBorderSoftDark = Color(0x1AFFFFFF)

val GlassFillLight = Color(0xB3FFFFFF)
val GlassFillStrongLight = Color(0xE6FFFFFF)
val GlassBorderLight = Color(0xFFCBDAF2)
val GlassBorderSoftLight = Color(0xFFDDE7F8)

// Dark scheme
val DarkPrimary = NeonBlueBright
val DarkOnPrimary = Color(0xFF04122B)
val DarkPrimaryContainer = Color(0xFF1C3E7A)
val DarkOnPrimaryContainer = Color(0xFFD8E6FF)
val DarkSecondary = Color(0xFFC4B5FD)
val DarkOnSecondary = Color(0xFF1C1145)
val DarkSecondaryContainer = Color(0xFF3A2A78)
val DarkOnSecondaryContainer = Color(0xFFE9E2FF)
val DarkTertiary = NeonCyan
val DarkOnTertiary = Color(0xFF002B33)
val DarkTertiaryContainer = Color(0xFF0A4753)
val DarkOnTertiaryContainer = Color(0xFFB8F3FA)
val DarkBackground = NavyDeep
val DarkOnBackground = Color(0xFFEAF1FF)
val DarkSurface = Color(0xFF0B1830)
val DarkOnSurface = Color(0xFFEAF1FF)
val DarkSurfaceVariant = Color(0xFF13294B)
val DarkOnSurfaceVariant = Color(0xFFA9BCDA)
val DarkOutline = Color(0xFF2B4470)
val DarkOutlineVariant = Color(0xFF1C3255)
val DarkSurfaceContainer = Color(0xFF0E1D38)
val DarkSurfaceContainerHigh = Color(0xFF152A4C)
val DarkSurfaceContainerLow = Color(0xFF0A172D)
val DarkError = Color(0xFFFF8A9B)
val DarkOnError = Color(0xFF4A0010)
val DarkErrorContainer = Color(0xFF5C1226)
val DarkOnErrorContainer = Color(0xFFFFD9DE)

// Light scheme (frosted daylight glass)
val LightPrimary = Color(0xFF2563EB)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFDCE7FF)
val LightOnPrimaryContainer = Color(0xFF081B3F)
val LightSecondary = Color(0xFF6D4DE0)
val LightOnSecondary = Color(0xFFFFFFFF)
val LightSecondaryContainer = Color(0xFFE8E1FF)
val LightOnSecondaryContainer = Color(0xFF1B1140)
val LightTertiary = Color(0xFF0891B2)
val LightOnTertiary = Color(0xFFFFFFFF)
val LightTertiaryContainer = Color(0xFFD3F3FA)
val LightOnTertiaryContainer = Color(0xFF00252E)
val LightBackground = Color(0xFFEAF2FF)
val LightOnBackground = Color(0xFF0A1730)
val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF0A1730)
val LightSurfaceVariant = Color(0xFFDCE7F8)
val LightOnSurfaceVariant = Color(0xFF47597A)
val LightOutline = Color(0xFFB9C9E2)
val LightOutlineVariant = Color(0xFFD5E1F3)
val LightSurfaceContainer = Color(0xFFF3F8FF)
val LightSurfaceContainerHigh = Color(0xFFE6EFFC)
val LightSurfaceContainerLow = Color(0xFFF8FAFF)
val LightError = Color(0xFFD92D4B)
val LightOnError = Color(0xFFFFFFFF)
val LightErrorContainer = Color(0xFFFFE0E4)
val LightOnErrorContainer = Color(0xFF4A0010)

// Gradients ----------------------------------------------------------------

/**
 * Interpolates extra stops between [colors]. Android renders gradients in 8 bits per channel,
 * which makes short gradients look banded/striped on dark screens; more stops keep the
 * transition visually smooth.
 */
fun smoothGradient(colors: List<Color>, stepsPerSegment: Int = 10): Brush {
    if (colors.size < 2) return Brush.linearGradient(listOf(colors.first(), colors.first()))
    val stops = buildList {
        add(0f to colors.first())
        for (i in 1 until colors.size) {
            val segmentStart = (i - 1).toFloat() / (colors.size - 1)
            val segmentEnd = i.toFloat() / (colors.size - 1)
            for (step in 1..stepsPerSegment) {
                val t = step.toFloat() / stepsPerSegment
                add(
                    (segmentStart + (segmentEnd - segmentStart) * t) to
                        lerp(colors[i - 1], colors[i], t)
                )
            }
        }
    }
    return Brush.linearGradient(*stops.toTypedArray())
}

val NeonButtonGradient = smoothGradient(listOf(Color(0xFF2E6BFF), Color(0xFF22A7F0)))
val NeonButtonGradientHorizontal = smoothGradient(listOf(Color(0xFF2E6BFF), Color(0xFF22C3F0)))
val BrandGradient = smoothGradient(listOf(Color(0xFF2E6BFF), Color(0xFF7C5CFF)))
val BannerGradient = smoothGradient(
    listOf(Color(0xFF4338CA), Color(0xFF1D4ED8), Color(0xFF0891B2)),
)
val GlowGradient = Brush.radialGradient(
    colors = listOf(Color(0x663B82F6), Color(0x00000000))
)

@Composable
fun isNight(): Boolean = LocalIsDarkTheme.current

@Composable
fun glassFill(): Color =
    if (isNight()) GlassFillDark else GlassFillLight

@Composable
fun glassFillStrong(): Color =
    if (isNight()) GlassFillStrongDark else GlassFillStrongLight

@Composable
fun glassCardBrush(): Brush =
    if (isNight()) {
        Brush.verticalGradient(listOf(Color(0x26FFFFFF), Color(0x16FFFFFF), Color(0x0FFFFFFF)))
    } else {
        Brush.verticalGradient(listOf(Color(0xD9FFFFFF), Color(0xCCFFFFFF), Color(0xAAFFFFFF)))
    }

@Composable
fun glassBorder(): Color =
    if (isNight()) GlassBorderDark else GlassBorderLight

@Composable
fun glassBorderSoft(): Color =
    if (isNight()) GlassBorderSoftDark else GlassBorderSoftLight

@Composable
fun neonAccent(): Color = MaterialTheme.colorScheme.primary

@Composable
fun appBackground(): Brush {
    return if (isNight()) {
        Brush.linearGradient(
            0f to Color(0xFF050B1A),
            0.22f to Color(0xFF071226),
            0.45f to Color(0xFF091A32),
            0.68f to Color(0xFF08162B),
            0.85f to Color(0xFF061022),
            1f to Color(0xFF050B1A),
        )
    } else {
        Brush.linearGradient(
            0f to Color(0xFFE9F2FF),
            0.25f to Color(0xFFE3EDFF),
            0.5f to Color(0xFFDCE9FF),
            0.75f to Color(0xFFE6F0FF),
            1f to Color(0xFFF4F8FF),
        )
    }
}

@Composable
fun heroGradient(): Brush {
    val background = MaterialTheme.colorScheme.background
    return if (isNight()) {
        Brush.verticalGradient(
            colors = listOf(Color(0x333B82F6), Color(0x141D4ED8), background.copy(alpha = 0f))
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color(0x33FFFFFF), Color(0x14FFFFFF), background.copy(alpha = 0f))
        )
    }
}
