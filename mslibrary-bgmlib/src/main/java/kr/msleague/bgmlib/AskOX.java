package kr.msleague.bgmlib;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AskOX {
    public static void askOX(Player player, String invname, String omsg, String xmsg) {
        Inventory inv = Bukkit.createInventory(null, 45, "§a§r"+invname);
        ItemStack glass = new ItemBuild(Material.STAINED_GLASS_PANE).displayname("§f").durability((byte) DyeColor.GRAY.ordinal()).build();
        for(int i=0; i<9; i++) {
            //ItemStack glass = ItemStackUtility.createItem(Material.STAINED_GLASS_PANE,1, "§f");
            inv.setItem(i, glass);
        }
        for(int i=36; i<45; i++) {
            //ItemStack glass = ItemStackUtility.createItem(Material.STAINED_GLASS_PANE,1, "§f");
            inv.setItem(i, glass);
        }
        //"&7안녕하세요"
        ItemStack oitem = new ItemBuild(Material.STAINED_CLAY).displayname("&7[ &a&lO &7]").addLore("&e ◇ &f"+omsg).durability((byte) DyeColor.LIME.ordinal()).build();
        ItemStack xitem = new ItemBuild(Material.STAINED_CLAY).displayname("&7[ &c&lX &7]").addLore("&e ◇ &f"+omsg).durability((byte) DyeColor.RED.ordinal()).build();
        inv.setItem(21, oitem);
        inv.setItem(23, xitem);
        player.openInventory(inv);
    }

}
