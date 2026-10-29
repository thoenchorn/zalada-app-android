package com.thoen.zaladaandroid.data.source

import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.data.model.CategoryDto
import com.thoen.zaladaandroid.data.model.ProductDto

/**
 * In-memory catalogue. Replace with a remote data source backed by Retrofit when the API is ready.
 */
class ProductLocalDataSource {

    private val categories = listOf(
        CategoryDto("phone", "Phone"),
        CategoryDto("laptop", "Laptop"),
        CategoryDto("monitor", "Monitor"),
        CategoryDto("headphone", "Headphone"),
        CategoryDto("mouse", "Mouse"),
        CategoryDto("keyboard", "Keyboard"),
        CategoryDto("speaker", "Speaker"),
        CategoryDto("microphone", "Microphone"),
    )

    private val products = listOf(
        ProductDto(
            id = 1,
            name = "Macbook Pro 2019 15” - Intel core i7",
            description = "The MacBook Pro is a line of Macintosh portable computers introduced in January 2006, by Apple Inc. It is the higher-end model of the MacBook family,",
            originalPrice = 1500.00,
            discountPrice = 1200.00,
            imageRes = R.drawable.mac,
            categoryId = "laptop",
            isNewArrival = true,
        ),
        ProductDto(
            id = 2,
            name = "Macbook Pro 2021 16 inch M1 Pro",
            description = "The M1 Pro chip brings a 10-core CPU and 16-core GPU to the MacBook Pro, with a Liquid Retina XDR display.",
            originalPrice = 1200.00,
            discountPrice = 1000.00,
            imageRes = R.drawable.mac,
            categoryId = "laptop",
            isNewArrival = false,
        ),
        ProductDto(
            id = 3,
            name = "New True Wireless Stereo Earbuds",
            description = "Compact true wireless earbuds with active noise cancellation and a pocket-sized charging case.",
            originalPrice = 500.00,
            discountPrice = 450.00,
            imageRes = R.drawable.mage1,
            categoryId = "headphone",
            isNewArrival = true,
        ),
        ProductDto(
            id = 4,
            name = "Sound White Wireless Portable",
            description = "Portable Bluetooth speaker with a punchy bass radiator and 12 hours of playback.",
            originalPrice = 250.00,
            discountPrice = 240.00,
            imageRes = R.drawable.mask,
            categoryId = "speaker",
            isNewArrival = false,
        ),
        ProductDto(
            id = 5,
            name = "UltraSharp 27 inch 4K Monitor",
            description = "A 27 inch 4K display with accurate colours and a slim bezel for multi-monitor setups.",
            originalPrice = 420.00,
            discountPrice = 380.00,
            imageRes = R.drawable.img,
            categoryId = "monitor",
            isNewArrival = true,
        ),
        ProductDto(
            id = 6,
            name = "Mechanical Keyboard RGB",
            description = "Hot-swappable mechanical keyboard with per-key RGB lighting and a aluminium case.",
            originalPrice = 130.00,
            discountPrice = 110.00,
            imageRes = R.drawable.img,
            categoryId = "keyboard",
            isNewArrival = false,
        ),
        ProductDto(
            id = 7,
            name = "Wireless Mouse Pro",
            description = "Ergonomic wireless mouse with an adjustable 4000 DPI sensor and silent switches.",
            originalPrice = 80.00,
            discountPrice = 65.00,
            imageRes = R.drawable.mage1,
            categoryId = "mouse",
            isNewArrival = false,
        ),
        ProductDto(
            id = 8,
            name = "Studio Microphone USB",
            description = "USB condenser microphone with a cardioid capsule, ideal for streaming and podcasts.",
            originalPrice = 210.00,
            discountPrice = 190.00,
            imageRes = R.drawable.img,
            categoryId = "microphone",
            isNewArrival = true,
        ),
        ProductDto(
            id = 9,
            name = "Over-Ear Headphones X",
            description = "Over-ear headphones with plush memory foam cushions and 40 hours of battery life.",
            originalPrice = 320.00,
            discountPrice = 280.00,
            imageRes = R.drawable.mage1,
            categoryId = "headphone",
            isNewArrival = false,
        ),
        ProductDto(
            id = 10,
            name = "Smartphone X 128GB",
            description = "A 6.1 inch OLED smartphone with a triple camera system and all-day battery.",
            originalPrice = 900.00,
            discountPrice = 820.00,
            imageRes = R.drawable.img,
            categoryId = "phone",
            isNewArrival = true,
        ),
    )

    suspend fun getProducts(): List<ProductDto> = products

    suspend fun getNewArrivals(): List<ProductDto> = products.filter { it.isNewArrival }

    suspend fun getPopularProducts(): List<ProductDto> = products.take(6)

    suspend fun searchProducts(query: String, categoryId: String?): List<ProductDto> =
        products.filter { product ->
            (categoryId == null || product.categoryId == categoryId) &&
                (query.isBlank() || product.name.contains(query, ignoreCase = true))
        }

    suspend fun getProductById(id: Int): ProductDto? = products.firstOrNull { it.id == id }

    suspend fun getCategories(): List<CategoryDto> = categories
}
