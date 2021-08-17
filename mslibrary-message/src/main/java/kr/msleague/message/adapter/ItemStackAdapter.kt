package kr.msleague.message.adapter

import kr.msleague.message.api.MSMessageAdapter
import kr.msleague.util.locale.*
import org.bukkit.inventory.ItemStack

class ItemStackAdapter: MSMessageAdapter<ItemStack> {
    override fun reformat(origin: String, obj: ItemStack): String =
        origin.replace("{item_name}", obj.l10nDisplayName)
            .replace("{item_amount}",obj.amount.toString())
            .replace("{item_type}", obj.friendlyName)
}