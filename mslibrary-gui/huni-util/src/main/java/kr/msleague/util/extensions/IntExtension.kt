package kr.msleague.util.extensions

import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("#,##0")
fun Int.toMoneyFormat(): String = decimalFormat.format(this)
fun Long.toMoneyFormat(): String = decimalFormat.format(this)
