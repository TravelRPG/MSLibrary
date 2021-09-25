package kr.msleague.bgmlib;

import kr.msleague.msgui.gui.MSGui;
import kr.msleague.msgui.gui.button.MSGuiButton;
import kr.msleague.msgui.gui.button.builder.MSGuiButtonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class AskOX extends MSGui<String> {

    private String xMsg;
    private String oMsg;
    public AskOX(Player player, String invName, String oMsg, String xMsg) {
        super(player, 45, "§a§r$invName", true, oMsg, xMsg);
    }

    public static void askOX(Player player, String invname, String omsg, String xmsg) {
        Inventory inv = Bukkit.createInventory(null, 45, "§a§r" + invname);
        ItemStack glass = new ItemBuild(Material.STAINED_GLASS_PANE).displayname("§f").durability((byte) DyeColor.GRAY.ordinal()).build();
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, glass);
        }
        for (int i = 36; i < 45; i++) {
            inv.setItem(i, glass);
        }
        ItemStack oitem = new ItemBuild(Material.STAINED_CLAY).displayname("&7[ &a&lO &7]").addLore("&e ◇ &f" + omsg).durability((byte) DyeColor.LIME.ordinal()).build();
        ItemStack xitem = new ItemBuild(Material.STAINED_CLAY).displayname("&7[ &c&lX &7]").addLore("&e ◇ &f" + omsg).durability((byte) DyeColor.RED.ordinal()).build();
        inv.setItem(21, oitem);
        inv.setItem(23, xitem);
        player.openInventory(inv);
    }

    @Override
    public void init() {
        oMsg = getObject(0);
        xMsg = getObject(1);
        MSGuiButton glass = new MSGuiButtonBuilder(Material.STAINED_GLASS_PANE, DyeColor.GRAY.ordinal()).build();
        MSGuiButton oItem = new MSGuiButtonBuilder(Material.STAINED_CLAY, DyeColor.LIME.ordinal())
                .setDisplayName("§7[ §a§lO §7]")
                .setLore(Collections.singletonList("&e ◇ &f" + oMsg))
                .build();
        MSGuiButton xItem = new MSGuiButtonBuilder(Material.STAINED_CLAY, DyeColor.LIME.ordinal())
                .setDisplayName("§7[ §a§lO §7]")
                .setLore(Collections.singletonList("&e ◇ &r" + xMsg))
                .build();
        int[] array = new int[18];
        for (int i = 0; i < 9; i++) {
            array[i] = i;
            array[i + 9] = i + 36;
        }
        glass.setSlot(this, array);
        oItem.setSlot(this, 21);
        xItem.setSlot(this, 23);
    }
}
