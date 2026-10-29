package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.ThemeMode
import com.thoen.zaladaandroid.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow

class ObserveThemeModeUseCase(private val themeRepository: ThemeRepository) {
    operator fun invoke(): Flow<ThemeMode> = themeRepository.observeThemeMode()
}
