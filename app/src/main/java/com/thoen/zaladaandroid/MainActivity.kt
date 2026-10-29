package com.thoen.zaladaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoen.zaladaandroid.core.di.ServiceLocator
import com.thoen.zaladaandroid.presentation.component.AppBackground
import com.thoen.zaladaandroid.presentation.navigation.AppRouter
import com.thoen.zaladaandroid.presentation.theme.LocalThemeController
import com.thoen.zaladaandroid.presentation.theme.ThemeController
import com.thoen.zaladaandroid.presentation.theme.ThemeViewModel
import com.thoen.zaladaandroid.presentation.theme.ZaladaAndroidTheme
import com.thoen.zaladaandroid.presentation.theme.resolveDark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(factory = ThemeViewModel.Factory)
            val themeMode by themeViewModel.themeMode.collectAsStateWithLifecycle()
            val themeController = remember(themeMode, themeViewModel) {
                ThemeController(
                    mode = themeMode,
                    onModeChange = themeViewModel::onThemeModeSelected,
                )
            }

            ZaladaAndroidTheme(darkTheme = themeMode.resolveDark()) {
                CompositionLocalProvider(LocalThemeController provides themeController) {
                    AppBackground {
                        AppRouter()
                    }
                }
            }
        }
    }
}
