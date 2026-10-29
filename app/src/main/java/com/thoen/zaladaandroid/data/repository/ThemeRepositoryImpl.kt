package com.thoen.zaladaandroid.data.repository

import com.thoen.zaladaandroid.data.source.ThemeLocalDataSource
import com.thoen.zaladaandroid.domain.model.ThemeMode
import com.thoen.zaladaandroid.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow

class ThemeRepositoryImpl(
    private val dataSource: ThemeLocalDataSource,
) : ThemeRepository {

    override fun observeThemeMode(): Flow<ThemeMode> = dataSource.themeMode

    override suspend fun setThemeMode(mode: ThemeMode) = dataSource.setThemeMode(mode)
}
