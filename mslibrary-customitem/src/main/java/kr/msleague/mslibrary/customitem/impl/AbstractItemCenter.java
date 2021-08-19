package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.ItemCenter;
import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import kr.msleague.mslibrary.customitem.api.ItemFactory;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class AbstractItemCenter<T> implements ItemCenter<T> {

    @Getter
    ItemDatabase database;
    @Getter
    ItemFactory<T> factory;

    public AbstractItemCenter(ItemDatabase database, ItemFactory<T> factory){
        this.database = database;
        this.factory = factory;
    }

    @Override
    public Optional<T> getItem(int id) {
        T result = null;
        try {
            MSItemData data = database.load(id).get();
            result = factory.build(data);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(result);

    }

    @Nonnull
    @Override
    public List<T> getItems(@Nonnull Class<T> clazz, @Nonnull String path, @Nonnull String value) {
        List<T> result = new ArrayList<>();
        try {
            List<Integer> list = database.search(path, value).get();
            for (int id : list) {
                MSItemData data = database.load(id).get();
                result.add(factory.build(data));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

}
