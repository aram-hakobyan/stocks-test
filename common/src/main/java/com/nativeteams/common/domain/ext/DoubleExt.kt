package com.nativeteams.common.domain.ext

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

const val DEFAULT_PATTERN = "#,##0.00"

fun Double.formatted(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        (this@apply as DecimalFormat).applyPattern(DEFAULT_PATTERN)
    }
    return formatter.format(this@formatted)
}

fun Pair<Double, Double>.growthPercentage(): Double {
    if (first == 0.0) {
        return 100.00
    }
    return ((second - first) / first) * 100
}
