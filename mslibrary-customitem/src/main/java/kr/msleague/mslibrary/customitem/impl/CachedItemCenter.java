package kr.msleague.mslibrary.customitem.impl;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import kr.msleague.mslibrary.customitem.api.ItemFactory;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedItemCenter<T> extends AbstractItemCenter<T> {

    ExecutorService service = Executors.newSingleThreadExecutor();
    LoadingCache<Integer, T> cache;

    public CachedItemCenter(ItemDatabase database, ItemFactory<T> factory) {
        super(database, factory);
        this.cache = Caffeine.newBuilder().build(new CacheLoader<Integer, T>() {
            @Override
            public T load(@NonNull Integer integer) throws Exception {
                return getItem0(integer).orElse(null);
            }
        });
    }

    public CachedItemCenter(ItemDatabase database, ItemFactory<T> factory, Caffeine<Integer, T> caffeine, CacheLoader<Integer, T> loader) {
        super(database, factory);
        this.cache = caffeine.build(loader);
    }

    @Nonnull
    @Override
    public Optional<T> getItem(int i) {
        return Optional.ofNullable(this.cache.get(i));
    }

    private Optional<T> getItem0(int i) {
        return super.getItem(i);
    }


    @Override
    public void load(boolean async) {
        if (async) {
            service.submit(this::load0);
        } else {
            load0();
        }
    }

    private void load0() {
        try {
            for (MSItemData itemData : database.loadAll().get()) {
                T item = factory.build(itemData);
                try {
                    cache.put(itemData.getID(), item);
                } catch (IllegalArgumentException ignored) {

                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh(int i, boolean async) {
        if (async) {
            service.submit(() -> cache.refresh(i));
            if (cache.get(i) == null) {
                cache.invalidate(i);
            }
        } else
            cache.refresh(i);
    }
}
