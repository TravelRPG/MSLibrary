package kr.msleague.msgui.managers

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import kr.msleague.msgui.highVersion
import org.apache.commons.codec.binary.Base64
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Field
import java.util.*

object SkullManager {

    private val headCacheMap = HashMap<UUID, ItemStack>()
    fun getHead(uuid: UUID?): ItemStack? = uuid?.run { headCacheMap[this] }
    fun setHead(uuid: UUID?, head: ItemStack) = uuid?.apply { headCacheMap[this] = head }

    private val base64Method by lazy { Class.forName("org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64").getMethod("encodeBase64",ByteArray::class.java) }
    private fun base64Func(array: ByteArray): ByteArray { return base64Method.invoke(null,array) as ByteArray }
    private val getMaterialMethod by lazy { Class.forName("org.bukkit.Material").getDeclaredMethod("getMaterial", String::class.java, Boolean::class.java) }
    private fun getMaterialFunc(name: String): Material = getMaterialMethod.invoke(null, name, false) as Material

    fun getSkull(temp: String, amount: Int): ItemStack {
        val head = if(highVersion) ItemStack(getMaterialFunc("PLAYER_HEAD"), amount) else ItemStack(Material.SKULL_ITEM, amount, 3.toShort())
        if (temp.isEmpty()) return head
        val url = "https://textures.minecraft.net/texture/$temp"
        val headMeta = head.itemMeta!!
        val profile = GameProfile(UUID.randomUUID(), null)
        val array = String.format("{textures:{SKIN:{url:\"%s\"}}}", url).toByteArray()
        val encodedData = if(highVersion) base64Func(array) else Base64.encodeBase64(array)
        profile.properties.put("textures", Property("textures", String(encodedData)))
        var profileField: Field? = null
        try {
            profileField = headMeta::class.java.getDeclaredField("profile")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
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