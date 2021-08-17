package kr.msleague.message.api

import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

interface MSMessage {
    val message: String
    val uncoloredMessage: String
    val coloredMessage: String
    fun sendMessage(vararg players: Player?)
    fun sendMessage(console: ConsoleCommandSender?)
    fun sendUnColoredMessage(vararg players: Player?)
    fun sendUnColoredMessage(console: ConsoleCommandSender?)
    fun reformat(obj: Any)
}