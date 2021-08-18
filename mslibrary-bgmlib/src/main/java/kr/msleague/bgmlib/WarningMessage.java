package kr.msleague.bgmlib;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WarningMessage {
    public static void sendWarningMsg(Player player, String msg) {
        player.sendTitle("§f", "§c[Error] §f"+msg, 10 ,60, 10);
        player.sendMessage("§c[Error] §f"+msg);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 1 , 1.1F);
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1.1F);
    }
    public static void sendGUIWarningMsg(Player player, Integer i, String msg) {
        Inventory inv = player.getOpenInventory().getTopInventory();
        if(inv == null) return;
        if(inv.getType() != InventoryType.CHEST) return;
        if(inv.getItem(i).getType() == Material.BARRIER) 
            return;

        ItemStack warning = new ItemBuild(Material.BARRIER).displayname("§c[Error] §f"+msg).build();
        ItemStack oitem = inv.getItem(i);
        inv.setItem(i, warning);
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1.1F);
        Bukkit.getScheduler().runTaskLater(BGMLibBukkit.main, ()->{
            //Inventory inv2 = player.getOpenInventory().getTopInventory();
            if(oitem==null) inv.setItem(i, new ItemStack(Material.AIR));
            else inv.setItem( i, oitem);
        }, 30);
    }
}
