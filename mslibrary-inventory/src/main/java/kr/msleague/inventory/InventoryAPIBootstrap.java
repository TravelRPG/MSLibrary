package kr.msleague.inventory;

import kr.msleague.bgmlib.ItemBuild;
import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.inventory.nms.V1_12_2;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class InventoryAPIBootstrap extends MSPlugin {
    @Override
    public void onEnable(){
        ItemStackSerializer serial = new ItemStackSerializer(new V1_12_2());
    }
}
