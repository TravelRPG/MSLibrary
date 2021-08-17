package kr.msleague.mslibrary.customitem.impl.node;

import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNodeArray;

import java.util.ArrayList;
import java.util.List;

public class ListItemNodeArray extends ItemElementImpl implements ItemNodeArray {

    List<ItemElement> list = new ArrayList<>();

    public ListItemNodeArray(ItemElement parents, String name) {
        super(parents, name);
    }

    @Override
    public void add(ItemElement node) {
        list.add(node);
    }

    @Override
    public void add(int index, ItemElement value) {
        list.add(index, value);
    }

    @Override
    public void remove(int index) {
        list.remove(index);
    }

    @Override
    public List<ItemElement> contents() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }
}
