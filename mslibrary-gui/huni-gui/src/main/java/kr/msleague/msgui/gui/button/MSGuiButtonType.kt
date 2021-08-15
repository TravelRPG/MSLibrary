package kr.msleague.msgui.gui.button

import org.bukkit.Material

enum class MSGuiButtonType(val defaultMaterial: Material, val durability: Int = 0) {
    CUSTOM_HEAD(Material.SKULL_ITEM),
    PLAYER_HEAD(Material.SKULL_ITEM, 3),
    ITEM_STACK(Material.AIR);
}