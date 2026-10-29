package com.thoen.zaladaandroid.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.presentation.component.WindowWidth
import com.thoen.zaladaandroid.presentation.component.rememberWindowWidth
import com.thoen.zaladaandroid.presentation.theme.smoothGradient
import kotlinx.coroutines.delay

data class PromoBannerPage(
    val badge: String,
    val title: String,
    val subtitle: String,
    val cta: String,
    @DrawableRes val imageRes: Int,
    val gradient: Brush,
)

val PromoBanners = listOf(
    PromoBannerPage(
        badge = "50% OFF",
        title = "Get 50% off\non your first order.",
        subtitle = "Order before midnight and save on your setup.",
        cta = "Shop now",
        imageRes = R.drawable.img,
        gradient = smoothGradient(listOf(Color(0xFF4F46E5), Color(0xFF2563EB), Color(0xFF0EA5E9))),
    ),
    PromoBannerPage(
        badge = "NEW",
        title = "MacBook Pro 2021\nis here.",
        subtitle = "M1 Pro power with all-day battery.",
        cta = "Explore",
        imageRes = R.drawable.mac,
        gradient = smoothGradient(listOf(Color(0xFF6D28D9), Color(0xFF4338CA), Color(0xFF2563EB))),
    ),
    PromoBannerPage(
        badge = "TRENDING",
        title = "True wireless\nearbuds.",
        subtitle = "Immersive sound in a pocket-sized case.",
        cta = "Discover",
        imageRes = R.drawable.mage1,
        gradient = smoothGradient(listOf(Color(0xFF0E7490), Color(0xFF0369A1), Color(0xFF1D4ED8))),
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerPager(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { PromoBanners.size })
    val windowWidth = rememberWindowWidth()
    val bannerRatio = when (windowWidth) {
        WindowWidth.Compact -> 1.58f
        WindowWidth.Medium -> 2.05f
        WindowWidth.Expanded -> 2.45f
    }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4_500)
            if (!pagerState.isScrollInProgress) {
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % PromoBanners.size
                )
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(end = 22.dp),
            pageSpacing = 12.dp,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            PromoBannerCard(
                page = PromoBanners[page],
                compact = windowWidth == WindowWidth.Compact,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(bannerRatio),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(PromoBanners.size) { index ->
                PageDot(selected = pagerState.currentPage == index)
            }
        }
    }
}

@Composable
private fun PageDot(selected: Boolean) {
    val width by animateDpAsState(targetValue = if (selected) 18.dp else 6.dp, label = "dot")
    Box(
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .height(6.dp)
            .width(width)
            .then(
                if (selected) {
                    Modifier.background(
                        Brush.horizontalGradient(listOf(Color(0xFF60A5FA), Color(0xFF22D3EE))),
                        CircleShape
                    )
                } else {
                    Modifier.background(Color.White.copy(alpha = 0.28f), CircleShape)
                }
            )
    )
}

@Composable
private fun PromoBannerCard(
    page: PromoBannerPage,
    compact: Boolean,
    modifier: Modifier = Modifier,
) {
    val shape = MaterialTheme.shapes.extraLarge
    Box(
        modifier = modifier
            .shadow(
                elevation = 18.dp,
                shape = shape,
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                clip = false,
            )
            .clip(shape)
            .background(page.gradient)
            .border(1.dp, Color.White.copy(alpha = 0.20f), shape)
    ) {
        // Soft light bloom behind the product image keeps the gradient edge smooth.
        Box(
            modifier = Modifier
                .size(230.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.24f),
                            Color.White.copy(alpha = 0.08f),
                            Color.White.copy(alpha = 0f),
                        )
                    ),
                    CircleShape
                )
        )

        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1.08f)
                    .fillMaxHeight()
                    .padding(horizontal = if (compact) 15.dp else 18.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.18f),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.25f)
                    ),
                ) {
                    Text(
                        text = page.badge,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = if (compact) 3.dp else 4.dp)
                    )
                }

                Column {
                    Text(
                        text = page.title,
                        style = if (compact) {
                            MaterialTheme.typography.titleMedium
                        } else {
                            MaterialTheme.typography.titleLarge
                        },
                        color = Color.White,
                        maxLines = 2,
                    )
                    if (!compact) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = page.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.82f),
                            maxLines = 2,
                        )
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    contentColor = Color(0xFF0B1220),
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = if (compact) 12.dp else 14.dp,
                            vertical = if (compact) 7.dp else 8.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = page.cta,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Image(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color(0xFF0B1220)),
                            modifier = Modifier
                                .size(14.dp)
                                .rotate(180f)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.92f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = page.imageRes),
                    contentDescription = page.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                )
            }
        }
    }
}
