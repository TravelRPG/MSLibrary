package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.MSItem;
import kr.msleague.mslibrary.customitem.api.ItemCenter;
import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import kr.msleague.mslibrary.customitem.api.ItemFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

public class ItemCenterImpl implements ItemCenter {
    @Override
    public ItemDatabase getDatabase() {
        return null;
    }

    @Nullable
    @Override
    public <T extends MSItem> ItemFactory<T> getFactory(Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends MSItem> void registerFactory(Class<T> clazz, @Nonnull ItemFactory<T> factory) throws IllegalArgumentException {

    }

    @Override
    public <T extends MSItem> Future<Optional<T>> getItem(int id, @Nonnull Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T extends MSItem> Future<List<T>> getItems(@Nonnull Class<T> clazz, @Nonnull String path, @Nonnull String value) {
        return null;
    }

    @Override
    public Future<Void> load(boolean async) {
        return null;
    }

    @Override
    public Future<Void> refresh(int itemID) {
        return null;
    }
}
