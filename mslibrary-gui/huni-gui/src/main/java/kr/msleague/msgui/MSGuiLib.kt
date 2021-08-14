package kr.msleague.msgui

import kr.msleague.bootstrap.MSPlugin

class MSGuiLib: MSPlugin() {

    override fun onEnable() {
        guiMainClass = this

    }

    override fun onDisable() {

    }

}