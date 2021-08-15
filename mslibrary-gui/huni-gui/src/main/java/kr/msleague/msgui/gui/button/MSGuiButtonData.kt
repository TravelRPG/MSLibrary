package kr.msleague.msgui.gui.button

import kr.msleague.msgui.gui.button.MSGuiButtonAction

data class MSGuiButtonData(
    val isCancelled: Boolean = false,
    val action: MSGuiButtonAction? = null
)
