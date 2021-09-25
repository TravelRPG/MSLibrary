package kr.msleague.mslibrary.customitem.utils.nms;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;

public class CustomNMSItem {

    private static Class<?> craftItemStackClass;
    private static Class<?> nbtTagCompoundClass;
    private static Method asNMSCopy;
    private static Method hasTag;
    private static Method getTag;
    private static Method setTag;
    private static Method getItemMeta;

    static {
        try {
            craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack");
            nbtTagCompoundClass = Class.forName("net.minecraft.server.v1_12_R1.NBTTagCompound");
            asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
            hasTag = nbtTagCompoundClass.getDeclaredMethod("hasTag");
            getTag = nbtTagCompoundClass.getDeclaredMethod("getTag");
            setTag = nbtTagCompoundClass.getDeclaredMethod("setTag", nbtTagCompoundClass);
            getItemMeta = craftItemStackClass.getDeclaredMethod("getItemMeta", Class.forName("net.minecraft.server.v1_12_R1.ItemStack"));
        } catch (Exception ignored) {}
    }

    private static Object asNMSCopy(ItemStack item) {
        try {
            return asNMSCopy.invoke(null, item);
        } catch (Exception e) {
            return null;
        }
    }

    private Object nmsItem;
    public CustomNMSItem(ItemStack itemStack) { nmsItem = asNMSCopy(itemStack); }

    private boolean hasTag() {
        try { return (boolean) hasTag.invoke(nmsItem);
        } catch (Exception ignored ) { return false; }
    }

    public CustomNBTTagCompound getTag() {
        try {
            if(!hasTag()) return new CustomNBTTagCompound(nbtTagCompoundClass.newInstance());
            else return new CustomNBTTagCompound(getTag.invoke(nmsItem));
        } catch (Exception e) { return null; }
    }

    public void setTag(CustomNBTTagCompound tag) {
        try {
            setTag.invoke(nmsItem, tag.nbtTagCompound);
        } catch (Exception ignored) {}
    }

    public ItemMeta getItemMeta() {
        try {
            return (ItemMeta) getItemMeta.invoke(null, nmsItem);
        } catch (Exception ignored) { return null; }
    }

}
