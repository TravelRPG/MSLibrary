package kr.msleague.util.locale

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

val ItemStack?.l10nDisplayName: String get() =
    if(this == null) "알 수 없음"
    else {
        if(hasItemMeta() && itemMeta.hasDisplayName()) itemMeta.displayName
        else LocaleQuery.getItemKey(type,0, "알 수 없음")
    }

val CraftItemStack?.l10nDisplayName: String get() =
    if(this == null) "알 수 없음"
    else {
        if(hasItemMeta() && itemMeta.hasDisplayName()) itemMeta.displayName
        else LocaleQuery.getItemKey(type,0, "알 수 없음")
    }

val ItemStack?.friendlyName: String get() =
    if(this == null) "알 수 없음"
    else LocaleQuery.getItemKey(type,0, "알 수 없음")

val CraftItemStack?.friendlyName: String get() =
    if(this == null) "알 수 없음"
    else LocaleQuery.getItemKey(type,0, "알 수 없음")