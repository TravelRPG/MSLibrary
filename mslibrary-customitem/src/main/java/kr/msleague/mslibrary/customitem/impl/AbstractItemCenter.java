package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.ItemCenter;
import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import kr.msleague.mslibrary.customitem.api.ItemFactory;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.MSLItemData;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public abstract class AbstractItemCenter<T> implements ItemCenter<T> {

    @Getter
    ItemDatabase database;
    @Getter
    ItemFactory<T> factory;

    public AbstractItemCenter(ItemDatabase database, ItemFactory<T> factory){
        this.database = database;
        this.factory = factory;
    }

    @Nonnull
    @Override
    public Optional<T> getItem(int id) {
        T result = null;
        try {
            MSItemData data = database.load(id).get();
            if(data != null)
                result = factory.build(data);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(result);

    }

    @Nonnull
    @Override
    public List<T> getItems(@Nonnull String path, @Nonnull String value) {
        List<T> result = new ArrayList<>();
        try {
            List<Integer> list = database.search(path, value).get();
            for (int id : list) {
                MSItemData data = database.load(id).get();
                if(data != null)
                    result.add(factory.build(data));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int register(T item) throws IllegalArgumentException {
        try {
            int id = database.size().get() + 1;
            MSItemData data = new MSLItemData(new HashItemNode(null, ""));
            data.getNodes().setPrimitive("id", id);
            data.getNodes().setPrimitive("version", 0);
            factory.parse(item, data);
            database.newItem(id, data);
            return id;
        }catch (Exception anyException){
            anyException.printStackTrace();
            throw new IllegalArgumentException("some exception occurred : "+anyException.getMessage());
        }
    }
}
