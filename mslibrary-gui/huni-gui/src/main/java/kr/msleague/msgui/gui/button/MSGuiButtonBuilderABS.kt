package kr.msleague.msgui.gui.button

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag

abstract class MSGuiButtonBuilderABS {

    abstract fun setDisplayName(displayName: String?): MSGuiButtonBuilderABS
    abstract fun setLore(lore: List<String>?): MSGuiButtonBuilderABS
    abstract fun addLore(line: String): MSGuiButtonBuilderABS
    abstract fun removeLore(index: Int): MSGuiButtonBuilderABS
    abstract fun addItemFlags(vararg itemFlags: ItemFlag): MSGuiButtonBuilderABS
    abstract fun setGlow(glow: Boolean): MSGuiButtonBuilderABS
    abstract fun setAction(action: MSGuiButtonAction): MSGuiButtonBuilderABS
    abstract fun setAmount(amount: Int): MSGuiButtonBuilderABS
    abstract fun setAction(ktFunc: (InventoryClickEvent)->Unit): MSGuiButtonBuilderABS
    abstract fun setCancellable(cancel: Boolean): MSGuiButtonBuilderABS
    abstract fun setCleanable(clean: Boolean): MSGuiButtonBuilderABS
    abstract fun build(): MSGuiButton
}