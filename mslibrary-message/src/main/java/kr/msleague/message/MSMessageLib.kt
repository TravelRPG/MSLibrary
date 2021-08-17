package kr.msleague.message

import kr.msleague.bootstrap.MSPlugin
import kr.msleague.message.adapter.CraftItemStackAdapter
import kr.msleague.message.adapter.CraftPlayerAdapter
import kr.msleague.message.adapter.ItemStackAdapter
import kr.msleague.message.adapter.PlayerAdapter
import kr.msleague.message.api.MSMessage
import kr.msleague.message.api.MSMessageAdapter
import kr.msleague.message.api.MSMessageRegistry
import kr.msleague.util.extensions.toColorString
import kr.msleague.util.extensions.toStripColorString
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class MSMessageLib: MSPlugin() {

    override fun onEnable() {
        registerAdapters(
            PlayerAdapter(),
            ItemStackAdapter(),
            CraftPlayerAdapter(),
            CraftItemStackAdapter()
        )
    }

    companion object {
        fun registerAdapters(vararg adapters: MSMessageAdapter<*>) { MSMessageRegistry.registerAdapters(*adapters) }
        fun reformat(origin: String, vararg objs: Any): MSMessage = object: MSMessage {
            private var base = MSMessageRegistry.reformat(origin, *objs)
            override val message: String get() = base
            override val coloredMessage: String get() = base.toColorString()
            override val uncoloredMessage: String get() = base.toStripColorString()
            override fun sendMessage(vararg players: Player?) { players.forEach{ it?.sendMessage(coloredMessage) } }
            override fun sendMessage(console: ConsoleCommandSender?) { console?.sendMessage(coloredMessage) }
            override fun sendUnColoredMessage(vararg players: Player?) { players.forEach{ it?.sendMessage(coloredMessage) } }
            override fun sendUnColoredMessage(console: ConsoleCommandSender?) { console?.sendMessage(uncoloredMessage) }
            override fun reformat(obj: Any) { base = MSMessageRegistry.reformat(base, obj) }
        }
    }

}