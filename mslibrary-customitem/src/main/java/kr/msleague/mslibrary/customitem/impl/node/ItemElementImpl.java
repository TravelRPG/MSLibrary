package kr.msleague.mslibrary.customitem.impl.node;

import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.ItemNodeArray;
import kr.msleague.mslibrary.customitem.api.ItemNodeValue;
import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public abstract class ItemElementImpl implements ItemElement {

    final ItemElement parents;
    String name;

    ItemElementImpl(ItemElement parents, String name) {
        this.parents = parents;
        this.name = name == null ? "" : name;
    }

    ItemElementImpl(ItemElement parents) {
        this(parents, null);
    }

    @Nonnull
    @Override
    public String getPath() {
        if (parents == null) {
            return name;
        } else if (name.equals("")) {
            return parents.getPath();
        } else if (parents.getName().equals("")) {
            return name;
        } else
            return parents.getPath() + "." + name;
    }

    @Nonnull
    @Override
    public ItemNode asNode() {
        if (this instanceof ItemNode)
            return (ItemNode) this;
        else
            throw new UnsupportedOperationException("cannot cast element to node");
    }

    @Nonnull
    @Override
    public ItemNodeArray asArray() {
        if (this instanceof ItemNodeArray)
            return (ItemNodeArray) this;
        else
            throw new UnsupportedOperationException("cannot cast element to array");
    }

    @Nonnull
    @Override
    public ItemNodeValue asValue() {
        if (this instanceof ItemNodeValue)
            return (ItemNodeValue) this;
        else
            throw new UnsupportedOperationException("cannot cast element to value");
    }
}
