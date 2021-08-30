package kr.msleague.bgmlib;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FastItem {
    public ItemStack getGreenClay() {
        return new ItemBuild(Material.CLAY).durability((byte) DyeColor.LIME.ordinal()).build();
    }

    public ItemStack getRedClay() {
        return new ItemBuild(Material.CLAY).durability((byte) DyeColor.RED.ordinal()).build();
    }
}
