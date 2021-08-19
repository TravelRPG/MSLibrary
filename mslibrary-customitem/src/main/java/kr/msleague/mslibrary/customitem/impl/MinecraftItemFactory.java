package kr.msleague.mslibrary.customitem.impl;

import com.google.common.collect.ImmutableMap;
import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemFactory;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import kr.msleague.mslibrary.customitem.impl.adapters.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Minecraft Item Factory
 * @since 1.0
 * @author Arkarang
 */
public class MinecraftItemFactory implements ItemFactory<ItemStack> {

    LinkedHashMap<String, ItemAdapter<ItemStack>> adapters = new LinkedHashMap<>();

    public MinecraftItemFactory(){
        add("material", new MaterialAdapter());
        add("displayname", new DisplaynameAdapter());
        add("enchant", new EnchantAdapter());
        add("lore", new LoreAdapter());
        add("attribute", new AttributeAdapter());
        add("unbreakable", new UnbreakableAdapter());
        add("itemflag", new ItemFlagAdapter());
    }

    @Nonnull
    @Override
    public Map<String, ItemAdapter<ItemStack>> getAdapters() {
        return ImmutableMap.copyOf(adapters);
    }

    @Override
    public void add(@Nonnull String tag, @Nonnull ItemAdapter<ItemStack> adapter) {
        if(!adapters.containsKey(tag))
            adapters.put(tag, adapter);
    }

    @Nonnull
    @Override
    public ItemStack build(@Nonnull MSItemData data) throws IllegalArgumentException {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
        for (ItemAdapter<ItemStack> adapter : adapters.values()) {
            adapter.read(itemStack, data);
        }
        return itemStack;
    }

}
