package kr.msleague.mslibrary.node.api;

import java.util.List;

//todo: documents this
public interface MSNodeArray extends MSNodeElement {

    //todo: documents this
    void add(MSNodeElement node);

    //todo: documents this
    void add(int index, MSNodeElement value);

    //todo: documents this
    void addPrimitive(Object primitive);

    //todo: documents this
    void remove(int index);

    //todo: documents this
    List<MSNodeElement> contents();

    //todo: documents this
    int size();
}
