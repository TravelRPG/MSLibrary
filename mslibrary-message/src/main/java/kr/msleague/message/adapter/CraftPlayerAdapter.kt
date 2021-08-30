package kr.msleague.message.adapter

import kr.msleague.message.api.MSMessageAdapter
import kr.msleague.util.extensions.roundString
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer

class CraftPlayerAdapter : MSMessageAdapter<CraftPlayer> {

    override fun request(obj: CraftPlayer, placeHolder: String): String? {
        return when (placeHolder) {
            "player_name" -> obj.name
            "player_health" -> obj.health.roundString()
            "player_max_health" -> obj.health.roundString()
            else -> null
        }
    }
}