package kr.msleague.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface MInventory {
    int getSize();
    void setSize(int size);
    ItemStack getItem(int slot);
    void setItem(int slot, ItemStack stack);
    Inventory asBukkitInventory(InventoryHolder owner, String name);
    void copyContents(Inventory to);
}
