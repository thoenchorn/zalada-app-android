package com.thoen.zaladaandroid.core.di

import android.content.Context

import com.thoen.zaladaandroid.data.repository.CartRepositoryImpl
import com.thoen.zaladaandroid.data.repository.FavoriteRepositoryImpl
import com.thoen.zaladaandroid.data.repository.NotificationRepositoryImpl
import com.thoen.zaladaandroid.data.repository.ProductRepositoryImpl
import com.thoen.zaladaandroid.data.repository.ThemeRepositoryImpl
import com.thoen.zaladaandroid.data.source.CartLocalDataSource
import com.thoen.zaladaandroid.data.source.FavoriteLocalDataSource
import com.thoen.zaladaandroid.data.source.NotificationLocalDataSource
import com.thoen.zaladaandroid.data.source.ProductLocalDataSource
import com.thoen.zaladaandroid.data.source.ThemeLocalDataSource
import com.thoen.zaladaandroid.domain.repository.CartRepository
import com.thoen.zaladaandroid.domain.repository.FavoriteRepository
import com.thoen.zaladaandroid.domain.repository.NotificationRepository
import com.thoen.zaladaandroid.domain.repository.ProductRepository
import com.thoen.zaladaandroid.domain.repository.ThemeRepository
import com.thoen.zaladaandroid.domain.usecase.AddToCartUseCase
import com.thoen.zaladaandroid.domain.usecase.GetCategoriesUseCase
import com.thoen.zaladaandroid.domain.usecase.GetNewArrivalsUseCase
import com.thoen.zaladaandroid.domain.usecase.GetNotificationsUseCase
import com.thoen.zaladaandroid.domain.usecase.GetPopularProductsUseCase
import com.thoen.zaladaandroid.domain.usecase.GetProductByIdUseCase
import com.thoen.zaladaandroid.domain.usecase.GetProductsUseCase
import com.thoen.zaladaandroid.domain.usecase.ObserveCartCountUseCase
import com.thoen.zaladaandroid.domain.usecase.ObserveCartUseCase
import com.thoen.zaladaandroid.domain.usecase.ObserveFavoritesUseCase
import com.thoen.zaladaandroid.domain.usecase.ObserveIsFavoriteUseCase
import com.thoen.zaladaandroid.domain.usecase.ObserveThemeModeUseCase
import com.thoen.zaladaandroid.domain.usecase.RemoveCartItemUseCase
import com.thoen.zaladaandroid.domain.usecase.SearchProductsUseCase
import com.thoen.zaladaandroid.domain.usecase.SetThemeModeUseCase
import com.thoen.zaladaandroid.domain.usecase.ToggleAllCartItemsUseCase
import com.thoen.zaladaandroid.domain.usecase.ToggleCartItemSelectionUseCase
import com.thoen.zaladaandroid.domain.usecase.ToggleFavoriteUseCase
import com.thoen.zaladaandroid.domain.usecase.UpdateCartQuantityUseCase

/**
 * Manual dependency graph used until a DI framework (Hilt/Koin) is introduced.
 * Every entry is created lazily and reused for the whole process.
 */
object ServiceLocator {

    private lateinit var appContext: Context

    fun init(context: Context) {
        if (!::appContext.isInitialized) {
            appContext = context.applicationContext
        }
    }

    private val productLocalDataSource by lazy { ProductLocalDataSource() }
    private val cartLocalDataSource by lazy { CartLocalDataSource() }
    private val favoriteLocalDataSource by lazy { FavoriteLocalDataSource() }
    private val notificationLocalDataSource by lazy { NotificationLocalDataSource() }
    private val themeLocalDataSource by lazy { ThemeLocalDataSource(appContext) }

    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(productLocalDataSource)
    }
    val cartRepository: CartRepository by lazy {
        CartRepositoryImpl(cartLocalDataSource, productRepository)
    }
    val favoriteRepository: FavoriteRepository by lazy {
        FavoriteRepositoryImpl(favoriteLocalDataSource, productRepository)
    }
    val notificationRepository: NotificationRepository by lazy {
        NotificationRepositoryImpl(notificationLocalDataSource)
    }
    val themeRepository: ThemeRepository by lazy {
        ThemeRepositoryImpl(themeLocalDataSource)
    }

    val getProductsUseCase by lazy { GetProductsUseCase(productRepository) }
    val getNewArrivalsUseCase by lazy { GetNewArrivalsUseCase(productRepository) }
    val getPopularProductsUseCase by lazy { GetPopularProductsUseCase(productRepository) }
    val getProductByIdUseCase by lazy { GetProductByIdUseCase(productRepository) }
    val getCategoriesUseCase by lazy { GetCategoriesUseCase(productRepository) }
    val searchProductsUseCase by lazy { SearchProductsUseCase(productRepository) }
    val getNotificationsUseCase by lazy { GetNotificationsUseCase(notificationRepository) }

    val observeCartUseCase by lazy { ObserveCartUseCase(cartRepository) }
    val observeCartCountUseCase by lazy { ObserveCartCountUseCase(cartRepository) }
    val addToCartUseCase by lazy { AddToCartUseCase(cartRepository) }
    val updateCartQuantityUseCase by lazy { UpdateCartQuantityUseCase(cartRepository) }
    val removeCartItemUseCase by lazy { RemoveCartItemUseCase(cartRepository) }
    val toggleCartItemSelectionUseCase by lazy { ToggleCartItemSelectionUseCase(cartRepository) }
    val toggleAllCartItemsUseCase by lazy { ToggleAllCartItemsUseCase(cartRepository) }

    val observeFavoritesUseCase by lazy { ObserveFavoritesUseCase(favoriteRepository) }
    val observeIsFavoriteUseCase by lazy { ObserveIsFavoriteUseCase(favoriteRepository) }
    val toggleFavoriteUseCase by lazy { ToggleFavoriteUseCase(favoriteRepository) }

    val observeThemeModeUseCase by lazy { ObserveThemeModeUseCase(themeRepository) }
    val setThemeModeUseCase by lazy { SetThemeModeUseCase(themeRepository) }
}
