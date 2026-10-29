package com.thoen.zaladaandroid.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoen.zaladaandroid.presentation.theme.NeonButtonGradientHorizontal
import com.thoen.zaladaandroid.presentation.theme.glassBorder
import com.thoen.zaladaandroid.presentation.theme.glassFill
import com.thoen.zaladaandroid.presentation.theme.isNight
import androidx.navigation.NavController
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.presentation.component.WindowWidth
import com.thoen.zaladaandroid.presentation.component.isCompact
import com.thoen.zaladaandroid.presentation.component.rememberWindowWidth
import com.thoen.zaladaandroid.presentation.favorite.FavoriteScreen
import com.thoen.zaladaandroid.presentation.home.HomeScreen
import com.thoen.zaladaandroid.presentation.main.model.NavItem
import com.thoen.zaladaandroid.presentation.notification.NotificationScreen
import com.thoen.zaladaandroid.presentation.profile.ProfileScreen

@Composable
fun MainScreen(navController: NavController) {
    val windowWidth = rememberWindowWidth()
    val items = listOf(
        NavItem("Home", R.drawable.home_bulk, R.drawable.home_bold),
        NavItem("Favorite", R.drawable.heart_bulk, R.drawable.heart_bold),
        NavItem("Alerts", R.drawable.notification_bulk, R.drawable.notification_bold),
        NavItem("Profile", R.drawable.profile_bulk, R.drawable.profile_bold),
    )

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    if (windowWidth.isCompact) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                MainBottomBar(
                    items = items,
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it },
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                ContentScreen(
                    selectedIndex = selectedIndex,
                    navController = navController,
                )
            }
        }
    } else {
        Row(modifier = Modifier.fillMaxSize()) {
            MainNavigationRail(
                items = items,
                selectedIndex = selectedIndex,
                onSelect = { selectedIndex = it },
                expanded = windowWidth != WindowWidth.Medium,
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .statusBarsPadding()
            ) {
                ContentScreen(
                    selectedIndex = selectedIndex,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
private fun MainBottomBar(
    items: List<NavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.55f),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.55f),
                    clip = false,
                )
                .clip(RoundedCornerShape(28.dp))
                .background(glassFill())
                .border(1.dp, glassBorder(), RoundedCornerShape(28.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    BottomBarItem(
                        item = item,
                        isSelected = index == selectedIndex,
                        onClick = { onSelect(index) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent
            )
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = if (isSelected) item.iconSelected else item.icon),
            contentDescription = item.title,
            tint = if (isSelected) Color.White
            else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) Color.White
            else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun MainNavigationRail(
    items: List<NavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    expanded: Boolean,
) {
    NavigationRail(
        containerColor = Color.Transparent,
        modifier = Modifier.fillMaxHeight(),
        header = {
            Column(
                modifier = Modifier.padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(NeonButtonGradientHorizontal),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Z",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
                if (expanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Zalada",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = index == selectedIndex,
                onClick = { onSelect(index) },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (index == selectedIndex) item.iconSelected else item.icon
                        ),
                        contentDescription = item.title
                    )
                },
                label = if (expanded) {
                    { Text(item.title) }
                } else {
                    null
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
        }
    }
}

@Composable
fun ContentScreen(
    navController: NavController,
    selectedIndex: Int = 0,
) {
    when (selectedIndex) {
        0 -> HomeScreen(navController)
        1 -> FavoriteScreen(navController)
        2 -> NotificationScreen(navController)
        3 -> ProfileScreen(navController)
    }
}
