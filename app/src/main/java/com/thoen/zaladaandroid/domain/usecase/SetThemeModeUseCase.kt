package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.ThemeMode
import com.thoen.zaladaandroid.domain.repository.ThemeRepository

class SetThemeModeUseCase(private val themeRepository: ThemeRepository) {
    suspend operator fun invoke(mode: ThemeMode) = themeRepository.setThemeMode(mode)
}
