package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.repository.FavoriteRepository

class ToggleFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(productId: Int) = favoriteRepository.toggleFavorite(productId)
}
