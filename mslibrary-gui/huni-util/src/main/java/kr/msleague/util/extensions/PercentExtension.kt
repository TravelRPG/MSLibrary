package kr.msleague.util.extensions

import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.util.AbstractMap
import kotlin.math.ln
import kotlin.random.Random

val random: Random by lazy { Random }

fun Double.percent(): Boolean {
    val temp = this
    if(temp >= 100.0) return true
    else if(temp <= 0.0) return false
    val suc = BigDecimal(this)
    var fail = BigDecimal("100.0")
    fail = fail.subtract(suc)
    val entry = mapOf(true to suc.toDouble(),false to fail.toDouble()).entries.stream()
    return entry
        .map { e-> AbstractMap.SimpleEntry(e.key, -ln(random.nextDouble()) /e.value) }
        .min(compareBy { it.value })
        .orElseThrow { IllegalArgumentException() }.key
}

fun Int.percent(): Boolean {
    val temp = toDouble()
    return temp.percent()
}

inline fun <reified T> Map<T,Double>.percent(): T {
    val random = Random
    val entry = entries.stream()
    return entry
        .map { e-> AbstractMap.SimpleEntry(e.key, -ln(random.nextDouble()) /e.value) }
        .min(compareBy { it.value })
        .orElseThrow { IllegalArgumentException() }.key
}
