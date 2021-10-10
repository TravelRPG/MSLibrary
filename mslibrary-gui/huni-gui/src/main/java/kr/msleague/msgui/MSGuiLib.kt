package kr.msleague.msgui

import kr.msleague.bootstrap.MSPlugin
import kr.msleague.msgui.gui.MSGui
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryInteractEvent
import java.util.logging.Level

class MSGuiLib : MSPlugin() {

    override fun onEnable() {
        plugin = getPlugin()
        server.pluginManager.registerEvent(InventoryInteractEvent::class.java, object: Listener {}, EventPriority.HIGHEST, { _,e ->
            val event = e as InventoryInteractEvent
            if(MSGui.guiMap.contains(event.inventory.title)) {
                event.whoClicked?.apply {
                    if(!MSGui.viewers.containsKey(uniqueId)) {
                        event.isCancelled = true
                        closeInventory()
                        getLogger().log(Level.WARNING,"올바르지 않은 접근으로 인해 $name 님의 gui 를 닫았습니다.")
                    }
                }
            }
        }, plugin)
    }

}