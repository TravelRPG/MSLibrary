package kr.msleague.msgui.gui.button.builder

import kr.msleague.msgui.gui.button.MSGuiButton
import kr.msleague.msgui.gui.button.MSGuiButtonAction
import kr.msleague.msgui.gui.button.MSGuiButtonBuilderABS
import kr.msleague.msgui.highVersion
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class MSGuiButtonBuilder {

    private lateinit var builder: MSGuiButtonBuilderABS

    constructor(material: Material) { builder = if(highVersion) MSGui16ButtonBuilder(material) else MSGui12ButtonBuilder(material) }
    constructor(baseItem: ItemStack) { builder = if(highVersion) MSGui16ButtonBuilder(baseItem) else MSGui12ButtonBuilder(baseItem) }
    constructor(material: Material, durability: Int)  { builder = if(highVersion) MSGui16ButtonBuilder(material, durability) else MSGui12ButtonBuilder(material, durability) }
    constructor(url: String) { builder = if(highVersion) MSGui16ButtonBuilder(url) else MSGui12ButtonBuilder(url) }
    constructor(offlinePlayer: OfflinePlayer) { builder = if(highVersion) MSGui16ButtonBuilder(offlinePlayer) else MSGui12ButtonBuilder(offlinePlayer) }
    fun setDisplayName(displayName: String?): MSGuiButtonBuilder {
        builder.setDisplayName(displayName)
        return this
    }

    fun setLore(lore: List<String>?): MSGuiButtonBuilder {
        builder.setLore(lore)
        return this
    }

    fun addLore(line: String): MSGuiButtonBuilder {
        builder.addLore(line)
        return this
    }

    fun removeLore(index: Int): MSGuiButtonBuilder {
        builder.removeLore(index)
        return this
    }
    fun addItemFlags(vararg itemFlags: ItemFlag): MSGuiButtonBuilder {
        builder.addItemFlags(*itemFlags)
        return this
    }

    fun setGlow(glow: Boolean): MSGuiButtonBuilder {
        builder.setGlow(glow)
        return this
    }

    fun setAction(action: MSGuiButtonAction): MSGuiButtonBuilder {
        builder.setAction(action)
        return this
    }

    fun setAmount(amount: Int): MSGuiButtonBuilder {
        builder.setAmount(amount)
        return this
    }

    fun setAction(ktFunc: (InventoryClickEvent)->Unit): MSGuiButtonBuilder {
        builder.setAction(ktFunc)
        return this
    }
    fun setCancellable(cancel: Boolean): MSGuiButtonBuilder {
        builder.setCancellable(cancel)
        return this
    }

    fun setCleanable(clean: Boolean): MSGuiButtonBuilder {
        builder.setCleanable(clean)
        return this
    }

    fun build(): MSGuiButton = builder.build()

}