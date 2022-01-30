package kr.msleague.inventory;

import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.inventory.nms.V1_12_2;
import kr.msleague.inventory.nms.V1_17_1;
import org.bukkit.Bukkit;

public class InventoryAPIBootstrap extends MSPlugin {
    @Override
    public void onEnable() {
        if(Bukkit.getVersion().contains("1.12")){
            ItemStackSerializer serial = new ItemStackSerializer(new V1_12_2());
        }else if(Bukkit.getVersion().contains("1.17")){
            ItemStackSerializer serial = new ItemStackSerializer(new V1_17_1());
        }
    }
}
