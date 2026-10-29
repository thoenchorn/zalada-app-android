package com.thoen.zaladaandroid.data.source

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoriteLocalDataSource {

    private val _productIds = MutableStateFlow(setOf(1, 3, 5, 8))
    val productIds: StateFlow<Set<Int>> = _productIds.asStateFlow()

    fun toggle(productId: Int) {
        _productIds.update { ids ->
            if (productId in ids) ids - productId else ids + productId
        }
    }
}
