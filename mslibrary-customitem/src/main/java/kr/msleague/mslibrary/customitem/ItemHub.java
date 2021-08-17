package kr.msleague.mslibrary.customitem;

import kr.msleague.mslibrary.customitem.api.ItemCenter;
import kr.msleague.mslibrary.customitem.impl.ItemCenterImpl;
import lombok.Getter;

public class ItemHub {

    @Getter
    private static final ItemCenter inst = new ItemCenterImpl();
}
