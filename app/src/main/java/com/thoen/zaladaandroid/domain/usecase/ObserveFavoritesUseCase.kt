package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavoritesUseCase(private val favoriteRepository: FavoriteRepository) {
    operator fun invoke(): Flow<List<Product>> = favoriteRepository.observeFavorites()
}
