package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.*;
import org.bukkit.inventory.ItemStack;


public class MinecraftItemHub extends CachedItemCenter<ItemStack> {

    public MinecraftItemHub(ItemDatabase database) {
        super(database, new MinecraftItemFactory());
    }
}

