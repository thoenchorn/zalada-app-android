package com.thoen.zaladaandroid.data.repository

import com.thoen.zaladaandroid.data.source.CartLocalDataSource
import com.thoen.zaladaandroid.data.source.ProductLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CartRepositoryImplTest {

    private val productRepository = ProductRepositoryImpl(ProductLocalDataSource())
    private val dataSource = CartLocalDataSource()
    private val cartRepository = CartRepositoryImpl(dataSource, productRepository)

    @Test
    fun cart_items_are_resolved_against_the_catalogue() = runBlocking {
        val items = cartRepository.observeCart().first()

        assertEquals(3, items.size)
        assertTrue(items.all { it.product.id > 0 })
        assertEquals(1690.0, items.sumOf { it.subtotal }, 0.001)
    }

    @Test
    fun updating_quantity_is_reflected_in_the_stream() = runBlocking {
        cartRepository.updateQuantity(productId = 2, quantity = 3)

        val item = cartRepository.observeCart().first().first { it.product.id == 2 }

        assertEquals(3, item.quantity)
        assertEquals(item.product.discountPrice * 3, item.subtotal, 0.001)
    }

    @Test
    fun removing_item_drops_it_from_the_cart() = runBlocking {
        cartRepository.removeItem(productId = 3)

        val items = cartRepository.observeCart().first()

        assertEquals(2, items.size)
        assertFalse(items.any { it.product.id == 3 })
    }

    @Test
    fun selecting_all_items_flips_every_checkbox() = runBlocking {
        cartRepository.setAllSelected(false)
        assertTrue(cartRepository.observeCart().first().none { it.isChecked })

        cartRepository.setAllSelected(true)
        assertTrue(cartRepository.observeCart().first().all { it.isChecked })
    }
}
