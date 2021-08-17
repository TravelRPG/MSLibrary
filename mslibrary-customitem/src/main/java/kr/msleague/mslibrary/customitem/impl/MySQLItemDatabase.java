package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.ItemDatabase;

import java.util.List;
import java.util.concurrent.Future;

public class MySQLItemDatabase implements ItemDatabase {
    @Override
    public Future<Integer> newItem(SerializedItem item) {
        return null;
    }

    @Override
    public Future<SerializedItem> load(int itemID) throws IllegalStateException {
        return null;
    }

    @Override
    public Future<Void> insertItem(int itemID, SerializedItem item, boolean override) throws IllegalArgumentException {
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
    public Future<Void> modify(int itemID, String node, String value) {
        return null;
    }
}
