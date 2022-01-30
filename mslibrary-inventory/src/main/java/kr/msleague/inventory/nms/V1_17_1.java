package kr.msleague.inventory.nms;

import kr.msleague.inventory.ItemStackSerializerAdapter;
import org.bukkit.inventory.ItemStack;

public class V1_17_1 implements ItemStackSerializerAdapter {
    @Override
    public byte[] serialize(ItemStack itemStack, boolean compress) {
        return new byte[0];
    }

    @Override
    public ItemStack deserialize(byte[] array) {
        return null;
    }
}
