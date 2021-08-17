package kr.msleague.message.adapter

import kr.msleague.message.api.MSMessageAdapter
import kr.msleague.util.locale.friendlyName
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack

class CraftItemStackAdapter: MSMessageAdapter<CraftItemStack> {
    override fun reformat(origin: String, obj: CraftItemStack): String =
        origin.replace("{item_name}", obj.friendlyName)
            .replace("{item_amount}",obj.amount.toString())
            .replace("{item_type}", obj.friendlyName)
}