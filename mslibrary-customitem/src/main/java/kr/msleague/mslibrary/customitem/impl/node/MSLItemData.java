package kr.msleague.mslibrary.customitem.impl.node;

import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MSLItemData implements kr.msleague.mslibrary.customitem.api.MSItemData {

    @Getter
    final ItemNode nodes;

    @Override
    public int getID() {
        ItemElement element = nodes.get("id");
        if (element == null)
            throw new IllegalArgumentException("this item is corrupted");
        else
            return element.asValue().getAsInt();
    }

    @Override
    public long getVersion() {
        ItemElement element = nodes.get("version");
        if (element == null)
            throw new IllegalArgumentException("this item is corrupted");
        else
            return element.asValue().getAsInt();
    }

}
