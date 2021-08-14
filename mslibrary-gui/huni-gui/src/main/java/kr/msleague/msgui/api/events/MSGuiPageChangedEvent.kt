package kr.msleague.msgui.api.events

import kr.msleague.msgui.api.interfaces.MSGui
import kr.msleague.msgui.api.interfaces.MSGuiViewer

class MSGuiPageChangedEvent(
    viewer: MSGuiViewer,
    gui: MSGui,
    val prevPage: Int,
    val currentPage: Int
): MSGuiEvent(viewer, gui)