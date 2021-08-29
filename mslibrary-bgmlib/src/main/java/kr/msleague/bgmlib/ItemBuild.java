package kr.msleague.bgmlib;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuild {
    private Material material;
    private int amount = 1;
    private byte bte = 0;
    private Short durability = null;
    private String displayName = null;
    private boolean unbreakable = false;
    private ArrayList<String> lore = new ArrayList<String>();
    private ArrayList<ItemFlag> flags = new ArrayList<ItemFlag>();

    public ItemBuild(Material mat) {
        this.material = mat;
    }

    public ItemBuild(ItemStack is) {
        this.material = is.getType();
        this.displayName = is.getItemMeta().getDisplayName();
        this.amount = is.getAmount();
        this.bte = is.getData().getData();
        this.durability = is.getDurability();
        this.lore = new ArrayList<>(is.getItemMeta().hasLore() ? is.getItemMeta().getLore() : new ArrayList<>());
    }

    public static ItemStack getItemStack(Material mat, byte data, String displayname, String[] lore) {
        ItemStack is = new ItemStack(mat);
        if (data != 0) {
            is = new ItemStack(mat, 1, (byte) data);
        }
        ItemMeta im = is.getItemMeta();

        if (displayname != null)
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
        if (lore != null) {
            List<String> result = new ArrayList<>();

            for (String sx : lore) {
                result.add(sx.replace("&", "§"));
            }
            im.setLore(result);
        }
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.setUnbreakable(true);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack getItemStack(Material mat, byte data, String displayname, String[] lore, short durability) {
        ItemStack is = new ItemStack(mat);
        if (data != 0) {
            is = new ItemStack(mat, 1, (byte) data);
        }
        ItemMeta im = is.getItemMeta();
        if (durability != -1)
            is.setDurability(durability);

        if (displayname != null)
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
        if (lore != null) {
            List<String> result = new ArrayList<>();

            for (String sx : lore) {
                result.add(sx.replace("&", "§"));
            }
            im.setLore(result);
        }
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.setUnbreakable(true);

        is.setItemMeta(im);
        return is;
    }

    public ItemStack build() {
        ItemStack ret = new ItemStack(material, amount, bte);
        ItemMeta im = ret.getItemMeta();
        if (displayName != null)
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        if (durability != null)
            ret.setDurability(durability);
        if (!lore.isEmpty())
            im.setLore(lore);
        im.setUnbreakable(unbreakable);
        for (ItemFlag flg : flags)
            im.addItemFlags(flg);
        ret.setItemMeta(im);

        return ret;
    }

    public ItemBuild amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuild displayname(String displayname) {
        this.displayName = displayname;
        return this;
    }

    public ItemBuild durability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuild durability(byte durability) {
        this.bte = durability;
        return this;
    }

    public ItemBuild unbreakable(boolean unbreak) {
        this.unbreakable = unbreak;
        return this;
    }

    public ItemBuild addLore(String lore) {
        this.lore.add(ChatColor.translateAlternateColorCodes('&', lore));
        return this;
    }

    public ItemBuild addFlag(ItemFlag flag) {
        this.flags.add(flag);
        return this;
    }
}


