package kr.msleague.message

import kr.msleague.bootstrap.MSPlugin
import kr.msleague.message.adapter.CraftItemStackAdapter
import kr.msleague.message.adapter.CraftPlayerAdapter
import kr.msleague.message.adapter.ItemStackAdapter
import kr.msleague.message.adapter.PlayerAdapter
import kr.msleague.message.api.MSMessageRegistry

class MSMessageLib: MSPlugin() {

    override fun onEnable() {
        MSMessageRegistry.registerAdapters(
            PlayerAdapter(),
            ItemStackAdapter(),
            CraftPlayerAdapter(),
            CraftItemStackAdapter()
        )
    }

}