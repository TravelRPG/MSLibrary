package kr.msleague.mslibrary.node.impl.node;

import kr.msleague.mslibrary.node.api.MSNodeArray;
import kr.msleague.mslibrary.node.api.MSNodeElement;

import java.util.ArrayList;
import java.util.List;

public class ListMSNodeArray extends MSNodeElementImpl implements MSNodeArray {

    List<MSNodeElement> list = new ArrayList<>();

    public ListMSNodeArray(MSNodeElement parents, String name) {
        super(parents, name);
    }

    @Override
    public void add(MSNodeElement node) {
        list.add(node);
    }

    @Override
    public void add(int index, MSNodeElement value) {
        list.add(index, value);
    }

    @Override
    public void addPrimitive(Object primitive) {
        if (primitive instanceof Number || primitive instanceof String || primitive instanceof Boolean)
            add(new ObjectMSNodeValue(this, primitive));
    }

    @Override
    public void remove(int index) {
        list.remove(index);
    }

    @Override
    public List<MSNodeElement> contents() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }
}
