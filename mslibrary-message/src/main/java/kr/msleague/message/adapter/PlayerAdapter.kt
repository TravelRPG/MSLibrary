package kr.msleague.message.adapter

import kr.msleague.util.extensions.roundString
import kr.msleague.message.api.MSMessageAdapter
import org.bukkit.entity.Player

class PlayerAdapter: MSMessageAdapter<Player> {

    override fun request(obj: Player, placeHolder: String): String? {
        return when(placeHolder) {
            "player_name" -> obj.name
            "player_health" -> obj.health.roundString()
            "player_max_health" -> obj.health.roundString()
            else -> null
        }
    }
}