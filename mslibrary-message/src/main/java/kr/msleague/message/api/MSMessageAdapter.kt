package kr.msleague.message.api

interface MSMessageAdapter<T> {
    fun request(obj: T, placeHolder: String): String?
}