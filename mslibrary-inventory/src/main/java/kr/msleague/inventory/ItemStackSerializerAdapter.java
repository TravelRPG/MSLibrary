package kr.msleague.inventory;

import org.bukkit.inventory.ItemStack;

public interface ItemStackSerializerAdapter {
    byte[] serialize(ItemStack itemStack, boolean compress);
    ItemStack deserialize(byte[] array);
}
