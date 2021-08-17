package kr.msleague.msgui

import kr.msleague.bootstrap.MSPlugin
import kr.msleague.msgui.bukkittest.TestGUI
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class MSGuiLib: MSPlugin(), Listener {

    override fun onEnable() {
        plugin = getPlugin()
        pluginManager.registerEvents(this, plugin)
    }

    override fun onDisable() {

    }

    @EventHandler
    fun a(e: AsyncPlayerChatEvent) {
        TestGUI(e.player)
    }

}