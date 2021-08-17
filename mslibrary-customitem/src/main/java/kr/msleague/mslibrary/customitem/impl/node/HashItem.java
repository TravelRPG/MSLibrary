package kr.msleague.mslibrary.customitem.impl.node;

import kr.msleague.mslibrary.customitem.api.MSItem;
import kr.msleague.mslibrary.customitem.api.ItemNodeValue;
import lombok.Getter;

public class HashItem implements MSItem {

    @Getter
    HashItemNode nodes;

    @Override
    public int getID() {
        ItemNodeValue value = nodes.get("id").asValue();
        if(value == null)
            throw new IllegalArgumentException("this item is corrupted");
        else
            return value.getAsInt();
    }

    @Override
    public long getVersion() {
        ItemNodeValue value = nodes.get("version").asValue();
        if(value == null)
            throw new IllegalArgumentException("this item is corrupted");
        else
            return value.getAsInt();
    }

}
