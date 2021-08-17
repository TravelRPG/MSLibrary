package kr.msleague.message.adapter

import kr.msleague.message.api.MSMessageAdapter
import kr.msleague.util.extensions.roundString
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer

class CraftPlayerAdapter: MSMessageAdapter<CraftPlayer> {
    override fun reformat(origin: String, obj: CraftPlayer): String =
        origin.replace("{player_name}",obj.name)
            .replace("{player_health}", obj.health.roundString())
            .replace("{player_max_health}", obj.healthScale.roundString())
}