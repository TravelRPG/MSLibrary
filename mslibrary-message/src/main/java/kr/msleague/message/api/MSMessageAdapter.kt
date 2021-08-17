package kr.msleague.message.api

interface MSMessageAdapter<T> {
    fun reformat(origin: String, obj: T): String
}