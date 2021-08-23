package kr.msleague.util.extensions

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.takeItem(itemStack: ItemStack, amount: Int) {
    var leftAmount = amount
    storageContents.filter { it != null && it.type != Material.AIR }.forEach {
        if (it.isSimilar(itemStack)) {
            val itemCount = it.amount
            if (it.amount >= leftAmount) it.amount -= leftAmount
            else it.amount = 0
            leftAmount -= itemCount
            if (leftAmount <= 0) return
        }
    }
}