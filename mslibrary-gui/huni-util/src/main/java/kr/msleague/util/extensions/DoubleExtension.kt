package kr.msleague.util.extensions

import kotlin.math.pow

fun Double.round(n: Int = 3): Double = this * 10.0.pow(n) / 10.0.pow(n)
fun Double.roundString(n: Int = 3): String = round(n).toString()
fun Float.round(n: Int = 3): Float = this.toDouble().round(n).toFloat()
fun Float.roundString(n: Int = 3): String = this.toDouble().roundString(n)