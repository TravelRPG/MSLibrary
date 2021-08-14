package kr.msleague.mslibrary.customitem.api;

//todo: implements / documents this.
public interface ItemNodeArray {

    void add(ItemNode node);

    void add(ItemNodeValue value);

    void add(int index, ItemNode node);

    void add(int index, ItemNodeValue value);

    void remove(int index);

    int size();
}
