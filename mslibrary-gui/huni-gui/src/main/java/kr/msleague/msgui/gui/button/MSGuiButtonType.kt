package kr.msleague.msgui.gui.button

import kr.msleague.msgui.highVersion16
import kr.msleague.msgui.highVersion17
import org.bukkit.Material

enum class MSGuiButtonType(val defaultMaterial: Material, val durability: Int = 0) {
    CUSTOM_HEAD(if(highVersion16 || highVersion17) Material.valueOf("PLAYER_HEAD") else Material.SKULL_ITEM),
    PLAYER_HEAD(if(highVersion16 || highVersion17) Material.valueOf("PLAYER_HEAD") else Material.SKULL_ITEM, 3),
    ITEM_STACK(Material.AIR);
}