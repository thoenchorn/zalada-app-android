package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class ObserveIsFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {
    operator fun invoke(productId: Int): Flow<Boolean> =
        favoriteRepository.observeIsFavorite(productId)
}
