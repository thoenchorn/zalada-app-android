package com.thoen.zaladaandroid.core.common

import java.text.NumberFormat
import java.util.Locale

fun Double.formatAsPrice(): String {
    val amount = NumberFormat.getNumberInstance(Locale.US).format(this)
    return "$$amount"
}
