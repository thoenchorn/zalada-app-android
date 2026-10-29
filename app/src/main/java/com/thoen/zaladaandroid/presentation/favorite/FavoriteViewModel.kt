package com.thoen.zaladaandroid.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.thoen.zaladaandroid.core.di.ServiceLocator
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.usecase.ObserveFavoritesUseCase
import com.thoen.zaladaandroid.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    observeFavorites: ObserveFavoritesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
) : ViewModel() {

    val favorites: StateFlow<List<Product>> = observeFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun onToggleFavorite(productId: Int) {
        viewModelScope.launch { toggleFavorite(productId) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                FavoriteViewModel(
                    ServiceLocator.observeFavoritesUseCase,
                    ServiceLocator.toggleFavoriteUseCase,
                )
            }
        }
    }
}
