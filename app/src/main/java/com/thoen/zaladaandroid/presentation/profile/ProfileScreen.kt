package com.thoen.zaladaandroid.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.domain.model.ThemeMode
import com.thoen.zaladaandroid.presentation.component.AlertDialogCustom
import com.thoen.zaladaandroid.presentation.component.GlassCard
import com.thoen.zaladaandroid.presentation.component.ShapeIcons
import com.thoen.zaladaandroid.presentation.component.horizontalPadding
import com.thoen.zaladaandroid.presentation.component.rememberWindowWidth
import com.thoen.zaladaandroid.presentation.navigation.Login
import com.thoen.zaladaandroid.presentation.profile.component.ItemProfile
import com.thoen.zaladaandroid.presentation.profile.component.SwitcherNotification
import com.thoen.zaladaandroid.presentation.profile.component.ThemeModeDialog
import com.thoen.zaladaandroid.presentation.theme.LocalThemeController
import com.thoen.zaladaandroid.presentation.theme.label

@Composable
fun ProfileScreen(navController: NavController) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    val themeController = LocalThemeController.current
    val horizontal = rememberWindowWidth().horizontalPadding

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = horizontal)
            .padding(top = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Profile",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            ShapeIcons(onClick = {}, icon = R.drawable.edit)
        }
        Spacer(modifier = Modifier.height(20.dp))

        GlassCard(
            shape = MaterialTheme.shapes.extraLarge,
            glow = true,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.thoen),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(74.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.size(14.dp))
                Column {
                    Text(
                        text = "Chorn THOEN",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "thoenchorn@gmail.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        SectionTitle("Account Settings")
        Spacer(modifier = Modifier.height(10.dp))
        SettingsCard {
            ItemProfile(icon = R.drawable.location_bulk, title = "Address")
            ItemProfile(icon = R.drawable.wallet, title = "Payment")
            ItemProfile(icon = R.drawable.time, title = "Order History")
            SwitcherNotification(
                icon = R.drawable.notification_bulk,
                title = "Notification",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        SectionTitle("Appearance")
        Spacer(modifier = Modifier.height(10.dp))
        SettingsCard {
            ItemProfile(
                icon = R.drawable.circles,
                title = "Theme",
                trailingText = themeController?.mode?.label() ?: ThemeMode.System.label(),
                onClick = {
                    if (themeController != null) showThemeDialog = true
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        SectionTitle("General")
        Spacer(modifier = Modifier.height(10.dp))
        SettingsCard {
            ItemProfile(icon = R.drawable.setting, title = "Change Language")
            ItemProfile(icon = R.drawable.password, title = "Change Password")
            ItemProfile(icon = R.drawable.done, title = "Privacy Policy")
            ItemProfile(icon = R.drawable.about, title = "About Us", end = true)
        }

        Spacer(modifier = Modifier.height(24.dp))
        SettingsCard {
            ItemProfile(
                icon = R.drawable.logout,
                title = "Logout",
                end = true,
                color = MaterialTheme.colorScheme.error,
                onClick = { showLogoutDialog = true }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }

    if (showThemeDialog && themeController != null) {
        ThemeModeDialog(
            current = themeController.mode,
            onSelect = { mode ->
                themeController.onModeChange(mode)
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false },
        )
    }

    if (showLogoutDialog) {
        AlertDialogCustom(
            onDismissRequest = { showLogoutDialog = false },
            onConfirmation = {
                showLogoutDialog = false
                navController.navigate(Login.route)
            },
            dialogTitle = "Logout",
            dialogText = "Are you sure you want to logout?",
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    GlassCard(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            content()
        }
    }
}

