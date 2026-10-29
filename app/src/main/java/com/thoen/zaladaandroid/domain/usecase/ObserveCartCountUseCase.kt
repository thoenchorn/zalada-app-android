package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveCartCountUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<Int> =
        cartRepository.observeCart().map { items -> items.sumOf { it.quantity } }
}
