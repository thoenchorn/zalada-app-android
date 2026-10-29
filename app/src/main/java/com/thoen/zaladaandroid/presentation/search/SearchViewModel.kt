package com.thoen.zaladaandroid.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.thoen.zaladaandroid.core.di.ServiceLocator
import com.thoen.zaladaandroid.domain.model.Category
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.usecase.AddToCartUseCase
import com.thoen.zaladaandroid.domain.usecase.GetCategoriesUseCase
import com.thoen.zaladaandroid.domain.usecase.SearchProductsUseCase
import com.thoen.zaladaandroid.presentation.search.model.SearchTab
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiState(
    val query: String = "",
    val categories: List<Category> = emptyList(),
    val selectedTabIndex: Int = 0,
    val results: List<Product> = emptyList(),
    val isLoading: Boolean = true,
) {
    val tabs: List<SearchTab>
        get() = listOf(SearchTab(id = null, title = "All")) +
            categories.map { SearchTab(id = it.id, title = it.name) }

    val selectedCategoryId: String?
        get() = if (selectedTabIndex == 0) null else categories.getOrNull(selectedTabIndex - 1)?.id
}

class SearchViewModel(
    private val getCategories: GetCategoriesUseCase,
    private val searchProducts: SearchProductsUseCase,
    private val addToCart: AddToCartUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            val categories = runCatching { getCategories() }.getOrDefault(emptyList())
            _uiState.update { it.copy(categories = categories, isLoading = false) }
            search()
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        search()
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
        search()
    }

    fun onAddToCart(product: Product) {
        viewModelScope.launch { addToCart(product) }
    }

    private fun search() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val state = _uiState.value
            val products = runCatching {
                searchProducts(state.query, state.selectedCategoryId)
            }.getOrDefault(emptyList())
            _uiState.update { it.copy(results = products) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    ServiceLocator.getCategoriesUseCase,
                    ServiceLocator.searchProductsUseCase,
                    ServiceLocator.addToCartUseCase,
                )
            }
        }
    }
}
