package kr.msleague.mslibrary.customitem.impl;

import com.google.gson.JsonObject;
import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import kr.msleague.mslibrary.customitem.api.MSItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Future;

public class JsonItemDatabase implements ItemDatabase<JsonObject> {


    @Override
    public Future<Integer> newItem(@Nonnull MSItem item) {
        return null;
    }

    @Override
    public Future<MSItem> load(int itemID) throws IllegalStateException {
        return null;
    }

    @Override
    public Future<Void> insertItem(int itemID, @Nonnull MSItem item, boolean override) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Future<Void> deleteItem(int itemID) {
        return null;
    }

    @Override
    public Future<List<Integer>> search(String path, String value) {
        return null;
    }

    @Override
    public Future<Void> modify(int itemID, @Nonnull String node, @Nullable String value) {
        return null;
    }
}
