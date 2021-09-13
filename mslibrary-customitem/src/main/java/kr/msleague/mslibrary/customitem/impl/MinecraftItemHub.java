package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import org.bukkit.inventory.ItemStack;


public class MinecraftItemHub extends CachedItemCenter<ItemStack> {

    public MinecraftItemHub(ItemDatabase database) {
        super(database, MinecraftItemFactory.v1_12());
    }

    public MinecraftItemHub(ItemDatabase database, MinecraftItemFactory factory) {
        super(database, factory);
    }
}

