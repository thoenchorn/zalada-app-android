package com.thoen.zaladaandroid.presentation.component

import androidx.annotation.DrawableRes
import com.thoen.zaladaandroid.R

/**
 * Maps catalogue category ids to the custom drawable icon set.
 */
object AppIcons {

    private val categories = mapOf(
        "phone" to R.drawable.mobile,
        "laptop" to R.drawable.laptop,
        "monitor" to R.drawable.monitor,
        "headphone" to R.drawable.headphones,
        "mouse" to R.drawable.mouse,
        "keyboard" to R.drawable.keyboard,
        "speaker" to R.drawable.speaker,
        "microphone" to R.drawable.microphone,
    )

    @DrawableRes
    fun category(categoryId: String?): Int =
        categories[categoryId] ?: R.drawable.circles
}
