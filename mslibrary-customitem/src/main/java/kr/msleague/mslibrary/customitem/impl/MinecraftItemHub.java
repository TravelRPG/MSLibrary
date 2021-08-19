package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.cache.api.MSAsyncLoadingCache;
import kr.msleague.mslibrary.customitem.api.*;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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

