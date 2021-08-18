package kr.msleague.msgui.api.event;

import kr.msleague.msgui.gui.MSGui;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class MSGuiButtonClickEvent extends MSGuiEvent implements Cancellable {
    private boolean cancelled;
    private static HandlerList handlerList = new HandlerList();
    private ClickType clickType;
    private InventoryAction action;
    private InventoryType.SlotType slotType;
    private int slot, rawSlot, hotBarKey;
    private ItemStack currentItem;
    ItemStack cursor;
    public MSGuiButtonClickEvent(Player player, MSGui<?> gui, ClickType clickType, InventoryAction action, InventoryType.SlotType slotType, int slot, int rawSlot, ItemStack currentItem, int hotBarKey, ItemStack cursor) {
        super(player, gui);
        this.clickType = clickType;
        this.action = action;
        this.slotType = slotType;
        this.slot = slot;
        this.rawSlot = rawSlot;
        this.currentItem = currentItem;
        this.hotBarKey = hotBarKey;
        this.cursor = cursor;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
