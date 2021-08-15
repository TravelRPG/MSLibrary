package kr.msleague.inventory;

import org.bukkit.inventory.ItemStack;

public class ItemStackSerializer implements ItemStackSerializerAdapter {
    private ItemStackSerializerAdapter adapter;
    private static ItemStackSerializer serializer;
    public ItemStackSerializer(ItemStackSerializerAdapter ad){
        adapter = ad;
        serializer = this;
    }
    @Override
    public byte[] serialize(ItemStack itemStack, boolean compress) {
        return adapter.serialize(itemStack, compress);
    }


    @Override
    public ItemStack deserialize(byte[] array) {
        return adapter.deserialize(array);
    }
    public static ItemStackSerializer getSerializer(){
        return serializer;
    }
}
