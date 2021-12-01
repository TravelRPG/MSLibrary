package kr.msleague.util.extensions

import kotlin.math.roundToInt

fun Double.round(n: Int = 3): Double = (this * (10 * n)).roundToInt() / (10 * n).toDouble()
fun Double.roundString(n: Int = 3): String = round(n).toString()
fun Float.round(n: Int = 3): Float = this.toDouble().round(n).toFloat()
fun Float.roundString(n: Int = 3): String = this.toDouble().roundString(n)