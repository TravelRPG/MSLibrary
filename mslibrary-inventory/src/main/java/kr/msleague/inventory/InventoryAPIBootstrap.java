package kr.msleague.inventory;

import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.inventory.nms.V1_12_2;

public class InventoryAPIBootstrap extends MSPlugin {
    @Override
    public void onEnable(){
        ItemStackSerializer serial = new ItemStackSerializer(new V1_12_2());
    }
}
