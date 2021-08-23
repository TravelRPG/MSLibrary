package kr.msleague.inventory;

import org.bukkit.inventory.ItemStack;

public class ItemStackSerializer implements ItemStackSerializerAdapter {
    private static ItemStackSerializer serializer;
    private ItemStackSerializerAdapter adapter;

    public ItemStackSerializer(ItemStackSerializerAdapter ad) {
        adapter = ad;
        serializer = this;
    }

    public static ItemStackSerializer getSerializer() {
        return serializer;
    }

    @Override
    public byte[] serialize(ItemStack itemStack, boolean compress) {
        return adapter.serialize(itemStack, compress);
    }

    @Override
    public ItemStack deserialize(byte[] array) {
        return adapter.deserialize(array);
    }
}
