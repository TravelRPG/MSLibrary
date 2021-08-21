package kr.msleague.message.api

import java.util.regex.Pattern

interface MSPlaceHolder {
    val head: String
    val tail: String
    val pattern: Pattern get() = Pattern.compile("[$head]([^${if(head != tail) "$head$tail" else head}]+)[$tail]")
}