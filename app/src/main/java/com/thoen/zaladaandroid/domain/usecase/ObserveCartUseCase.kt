package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.CartItem
import com.thoen.zaladaandroid.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class ObserveCartUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<List<CartItem>> = cartRepository.observeCart()
}
