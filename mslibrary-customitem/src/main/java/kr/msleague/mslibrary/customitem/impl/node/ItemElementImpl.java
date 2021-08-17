package kr.msleague.mslibrary.customitem.impl.node;

import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.ItemNodeArray;
import kr.msleague.mslibrary.customitem.api.ItemNodeValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ItemElementImpl implements ItemElement {

    final ItemElement parents;
    final String name;

    @Override
    public String getPath() {
        return parents.getPath() + "." + name;
    }

    @Override
    public ItemNode asNode() {
        return this instanceof ItemNode ? (ItemNode)this : null;
    }

    @Override
    public ItemNodeArray asArray() {
        return this instanceof ItemNodeArray ? (ItemNodeArray)this : null;
    }

    @Override
    public ItemNodeValue asValue() {
        return this instanceof ItemNodeValue ? (ItemNodeValue)this : null;
    }
}
