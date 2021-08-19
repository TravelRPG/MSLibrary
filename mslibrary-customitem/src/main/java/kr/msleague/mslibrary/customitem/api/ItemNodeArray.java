package kr.msleague.mslibrary.customitem.api;

import java.util.List;

//todo: documents this
public interface ItemNodeArray extends ItemElement{

    //todo: documents this
    void add(ItemElement node);

    //todo: documents this
    void add(int index, ItemElement value);

    //todo: documents this
    void addPrimitive(Object primitive);

    //todo: documents this
    void remove(int index);

    //todo: documents this
    List<ItemElement> contents();

    //todo: documents this
    int size();
}
