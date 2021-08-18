package kr.msleague.util.extensions

inline fun Any.checkFunc(block: ()->Any): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis()-start
}