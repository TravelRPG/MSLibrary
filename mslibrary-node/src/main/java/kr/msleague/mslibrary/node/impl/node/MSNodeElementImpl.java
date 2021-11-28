package kr.msleague.mslibrary.node.impl.node;

import kr.msleague.mslibrary.node.api.MSNode;
import kr.msleague.mslibrary.node.api.MSNodeArray;
import kr.msleague.mslibrary.node.api.MSNodeElement;
import kr.msleague.mslibrary.node.api.MSNodeValue;
import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public abstract class MSNodeElementImpl implements MSNodeElement {

    final MSNodeElement parents;
    String name;

    MSNodeElementImpl(MSNodeElement parents, String name) {
        this.parents = parents;
        this.name = name == null ? "" : name;
    }

    MSNodeElementImpl(MSNodeElement parents) {
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
    public MSNode asNode() {
        if (this instanceof MSNode)
            return (MSNode) this;
        else
            throw new UnsupportedOperationException("cannot cast element to node");
    }

    @Nonnull
    @Override
    public MSNodeArray asArray() {
        if (this instanceof MSNodeArray)
            return (MSNodeArray) this;
        else
            throw new UnsupportedOperationException("cannot cast element to array");
    }

    @Nonnull
    @Override
    public MSNodeValue asValue() {
        if (this instanceof MSNodeValue)
            return (MSNodeValue) this;
        else
            throw new UnsupportedOperationException("cannot cast element to value");
    }
}
