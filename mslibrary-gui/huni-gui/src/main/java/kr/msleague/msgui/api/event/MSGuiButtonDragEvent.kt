package kr.msleague.msgui.api.event

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class MSGuiButtonDragEvent(
    player: Player,
    what: InventoryView?,
    newCursor: ItemStack?,
    oldCursor: ItemStack?,
    right: Boolean,
    slots: MutableMap<Int, ItemStack>?
) {



}