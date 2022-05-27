package kr.msleague.util

import kr.msleague.bootstrap.MSPlugin
import kr.msleague.util.module.plugin

class MSLibraryUtil: MSPlugin() {

    override fun onEnable() {
        plugin = getPlugin()
    }

}