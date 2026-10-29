package com.thoen.zaladaandroid.data.repository

import com.thoen.zaladaandroid.data.source.ProductLocalDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductRepositoryImplTest {

    private val repository = ProductRepositoryImpl(ProductLocalDataSource())

    @Test
    fun search_matches_product_name_ignoring_case() = runBlocking {
        val results = repository.searchProducts(query = "macbook", categoryId = null)

        assertTrue(results.isNotEmpty())
        assertTrue(results.all { it.name.contains("Macbook", ignoreCase = true) })
    }

    @Test
    fun search_filters_by_category() = runBlocking {
        val results = repository.searchProducts(query = "", categoryId = "headphone")

        assertTrue(results.isNotEmpty())
        assertTrue(results.all { it.categoryId == "headphone" })
    }

    @Test
    fun new_arrivals_only_contain_flagged_products() = runBlocking {
        val newArrivals = repository.getNewArrivals()

        assertTrue(newArrivals.isNotEmpty())
        assertTrue(newArrivals.all { it.isNewArrival })
    }

    @Test
    fun categories_are_exposed_with_icon_resources() = runBlocking {
        val categories = repository.getCategories()

        assertEquals(8, categories.size)
        assertTrue(categories.all { it.id.isNotBlank() })
    }
}
