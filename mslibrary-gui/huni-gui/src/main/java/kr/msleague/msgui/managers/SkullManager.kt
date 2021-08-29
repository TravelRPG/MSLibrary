package kr.msleague.msgui.managers

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.apache.commons.codec.binary.Base64
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Field
import java.util.*

object SkullManager {
    fun getSkull(temp: String, amount: Int): ItemStack {
        val head = ItemStack(Material.SKULL_ITEM, amount, 3.toShort())
        if (temp.isEmpty()) return head
        val url = "https://textures.minecraft.net/texture/$temp"
        val headMeta = head.itemMeta
        val profile = GameProfile(UUID.randomUUID(), null)
        val encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).toByteArray())
        profile.properties.put("textures", Property("textures", String(encodedData)))
        var profileField: Field? = null
        try {
            profileField = headMeta.javaClass.getDeclaredField("profile")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        profileField?.isAccessible = true
        try {
            profileField?.set(headMeta, profile)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        head.itemMeta = headMeta
        return head
    }
}