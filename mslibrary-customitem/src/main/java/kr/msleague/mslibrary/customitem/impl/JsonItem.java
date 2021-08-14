package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JsonItem extends ItemJsonNodeValue implements SerializedItem {

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public long getVersion() {
        return 0;
    }

    @Nullable
    @Override
    public ItemElement getParents() {
        return null;
    }

    @Nonnull
    @Override
    public String getPath() {
        return null;
    }

    @Nullable
    @Override
    public ItemNodeValue getElement(@Nonnull String path) {
        return null;
    }

    @Nullable
    @Override
    public ItemNode getNode(@Nonnull String path) {
        return null;
    }

    @Nonnull
    @Override
    public ItemNodeArray getArray(@Nonnull String path) {
        return null;
    }

    @Override
    public boolean set(@Nonnull String path, @Nullable ItemNode node) {
        return false;
    }

    @Override
    public boolean set(@Nonnull String path, @Nullable ItemNodeValue element) {
        return false;
    }
}
