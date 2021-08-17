package kr.msleague.message.adapter

import kr.msleague.util.extensions.roundString
import kr.msleague.message.api.MSMessageAdapter
import org.bukkit.entity.Player

class PlayerAdapter: MSMessageAdapter<Player> {
    override fun reformat(origin: String, obj: Player): String =
        origin.replace("{player_name}",obj.name)
            .replace("{player_health}", obj.health.roundString())
            .replace("{player_max_health}", obj.healthScale.roundString())
}