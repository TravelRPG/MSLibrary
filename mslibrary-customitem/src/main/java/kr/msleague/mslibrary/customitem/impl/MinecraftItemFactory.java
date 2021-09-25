package kr.msleague.mslibrary.customitem.impl;

import com.google.common.collect.ImmutableMap;
import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemFactory;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import kr.msleague.mslibrary.customitem.impl.adapters.*;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.MSLItemData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Minecraft Item Factory
 *
 * @author Arkarang
 * @since 1.0
 */
public class MinecraftItemFactory implements ItemFactory<ItemStack> {

    LinkedHashMap<String, ItemAdapter<ItemStack>> adapters = new LinkedHashMap<>();

    public static MinecraftItemFactory v1_12(){
        MinecraftItemFactory factory = new MinecraftItemFactory();
        factory.add("material", new MaterialAdapter());
        factory.add("displayname", new DisplaynameAdapter());
        factory.add("enchant", new EnchantAdapter());
        factory.add("lore", new LoreAdapter());
        factory.add("attribute", new v1_12AttributeAdapter());
        factory.add("unbreakable", new UnbreakableAdapter());
        factory.add("itemflag", new ItemFlagAdapter());
        return factory;
    }

    public static MinecraftItemFactory v1_16(){
        MinecraftItemFactory factory = new MinecraftItemFactory();
        factory.add("material", new MaterialAdapter());
        factory.add("displayname", new DisplaynameAdapter());
        factory.add("enchant", new EnchantAdapter());
        factory.add("lore", new LoreAdapter());
        factory.add("attribute", new v1_16AttributeAdapter());
        factory.add("unbreakable", new UnbreakableAdapter());
        factory.add("itemflag", new ItemFlagAdapter());
        return factory;
    }

    /*public static MinecraftItemFactory getInstance() {
        MinecraftItemFactory factory = new MinecraftItemFactory();
        factory.add("material", new MaterialAdapter());
        factory.add("displayname", new DisplaynameAdapter());
        factory.add("enchant", new EnchantAdapter());
        factory.add("lore", new LoreAdapter());
        factory.add("attribute", new v1_12AttributeAdapter());
        factory.add("unbreakable", new UnbreakableAdapter());
        factory.add("itemflag", new ItemFlagAdapter());
        return factory;
    }*/

    private String version;
    private MinecraftItemFactory() {
        version = Bukkit.getServer().getClass().getPackage().getName();
    }

    @Nonnull
    @Override
    public Map<String, ItemAdapter<ItemStack>> getAdapters() {
        return ImmutableMap.copyOf(adapters);
    }

    @Override
    public void add(@Nonnull String tag, @Nonnull ItemAdapter<ItemStack> adapter) {
        if (!adapters.containsKey(tag))
            adapters.put(tag, adapter);
    }

    @Nonnull
    @Override
    public ItemStack build(@Nonnull MSItemData data) throws IllegalArgumentException {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
        return build(data, itemStack);
    }

    @Nonnull
    @Override
    public ItemStack build(@Nonnull MSItemData data, ItemStack target) throws IllegalArgumentException {
        for (ItemAdapter<ItemStack> adapter : adapters.values()) {
            adapter.read(target, data);
        }
        return target;
    }

    @Override
    public MSItemData parse(ItemStack item) throws IllegalArgumentException {
        MSItemData data = new MSLItemData(new HashItemNode(null, ""));
        return parse(item, data);
    }

    @Override
    public MSItemData parse(ItemStack item, MSItemData data) throws IllegalArgumentException {
        for (ItemAdapter<ItemStack> adapter : adapters.values()) {
            adapter.write(data, item);
        }
        return data;
    }

}
