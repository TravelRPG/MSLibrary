package kr.msleague.util;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemStackLocaleUtilKt {

    /**
     * 아이템의 l10nDisplayName 을 얻어옵니다.
     * ItemMeta 의 DisplayName 이 있다면, DisplayName 이 반환됩니다.
     *
     * @param item ItemStack
     * @return l10nDisplayName
     */
    public static String getL10nDisplayName(ItemStack item) {
        return "";
    }

    /**
     * 아이템의 l10nDisplayName 을 얻어옵니다.
     * ItemMeta 의 DisplayName 이 있다면 DisplayName 이 반환됩니다.
     *
     * @param item CraftItemStack
     * @return l10nDisplay
     */
    public static String getL10nDisplayName(CraftItemStack item) {
        return "";
    }

    /**
     * 아이템의 FriendlyName 을 얻어옵니다.
     * ItemMeta 의 DisplayName 을 무시합니다.
     *
     * @param item ItemStack
     * @return FriendlyName
     */
    public static String getFriendlyName(ItemStack item) {
        return "";
    }

    /**
     * 아이템의 FriendlyName 을 얻어옵니다.
     * ItemMeta 의 DisplayName 을 무시합니다.
     *
     * @param item CraftItemStack
     * @return FriendlyName
     */
    public static String getFriendlyName(CraftItemStack item) {
        return "";
    }

}
