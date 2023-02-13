package kr.msleague.msgui

import kr.msleague.bootstrap.MSPlugin
import kr.msleague.msgui.gui.MSGui
import kr.msleague.msgui.gui.button.MSGuiButtonData
import kr.msleague.util.extensions.ItemStackExtension19.getNBTTagCompound19
import kr.msleague.util.locale.l10nDisplayName
import org.bukkit.Material
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
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
        server.pluginManager.registerEvent(InventoryCloseEvent::class.java, object: Listener {}, EventPriority.HIGHEST, { _, e ->
            val event = e as InventoryCloseEvent
            val buttons = event.player.inventory.contents.filter { it != null && it.type != Material.AIR && it.getNBTTagCompound19(MSGuiButtonData::class.java) != null }
            if(buttons.isNotEmpty()) {
                buttons.forEach {
                    getLogger().warning("${event.player.name} 님이 (name=${it.l10nDisplayName})(amount=${it.amount})(type=${it.type}) 버튼을 가지고있어 삭제했습니다.")
                    it.amount = 0
                }
                event.player.sendMessage("§c올바르지 않은 접근으로 관리자에게 메세지를 보냈습니다.")
            }
        }, plugin)
    }

}