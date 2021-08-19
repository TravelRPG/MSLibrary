package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.ItemCenter;
import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import kr.msleague.mslibrary.customitem.api.ItemFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class CachedItemHub<T> implements ItemCenter<T> {

    LoadingCache<K, V> cache;

    @Override
    public ItemDatabase getDatabase() {
        return null;
    }

    @Nullable
    @Override
    public ItemFactory<T> getFactory() {
        return null;
    }

    @Override
    public Optional<T> getItem(int id) {
        return Optional.empty();
    }

    @Nonnull
    @Override
    public List<T> getItems(@Nonnull Class<T> clazz, @Nonnull String path, @Nonnull String value) {
        return null;
    }

    @Override
    public void load(boolean async) {

    }

    @Override
    public void refresh(int itemID, boolean async) {

    }
}
