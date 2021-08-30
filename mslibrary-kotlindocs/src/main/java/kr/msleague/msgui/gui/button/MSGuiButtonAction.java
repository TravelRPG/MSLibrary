package kr.msleague.msgui.gui.button;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface MSGuiButtonAction {
    /**
     * 버튼 클릭 시, 실행되는 메서드.
     *
     * @param e InventoryClickEvent
     */
    public void action(InventoryClickEvent e);

}


