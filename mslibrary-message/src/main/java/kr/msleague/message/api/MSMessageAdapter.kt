package kr.msleague.message.api

import javax.annotation.Nullable

interface MSMessageAdapter<T> {
    @Nullable fun request(obj: T, placeHolder: String): String?
}