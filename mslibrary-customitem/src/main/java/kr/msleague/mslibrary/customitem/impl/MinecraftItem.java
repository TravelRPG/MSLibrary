package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.MSItem;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import lombok.Getter;

public class MinecraftItem implements MSItem {

    @Getter
    ItemNode nodes;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public long getVersion() {
        return 0;
    }

}
