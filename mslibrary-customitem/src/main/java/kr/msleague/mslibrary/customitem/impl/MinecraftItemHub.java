package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.*;
import org.bukkit.inventory.ItemStack;


public class MinecraftItemHub extends AbstractItemCenter<ItemStack> {

    public MinecraftItemHub(ItemDatabase database) {
        super(database, new MinecraftItemFactory());
    }

    @Override
    public void load(boolean async) {

    }

    @Override
    public void refresh(int itemID, boolean async) {

    }
}

