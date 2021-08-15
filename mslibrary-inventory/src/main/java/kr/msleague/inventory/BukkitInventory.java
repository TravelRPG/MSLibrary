package kr.msleague.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BukkitInventory implements MInventory{
    @Getter
    @Setter
    private int size;
    private HashMap<Integer, ItemStack> items = new HashMap<>();
    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.put(slot, stack);
    }

    @Override
    public Inventory asBukkitInventory(InventoryHolder owner, String name) {
        Inventory inv = Bukkit.createInventory(owner, getSize(), name);
        items.forEach(inv::setItem);
        return null;
    }

    public static BukkitInventory fromBukkitInventory(Inventory inv){
        BukkitInventory var = new BukkitInventory();
        var.setSize(inv.getSize());
        ItemStack stack;
        for(int i = 0; i < inv.getSize(); i++){
            if((stack=inv.getItem(i))!=null){
                var.setItem(i, stack);
            }
        }
        return var;
    }
}
