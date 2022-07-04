package kr.msleague.msgui.managers

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import kr.msleague.msgui.highVersion16
import kr.msleague.msgui.highVersion17
import kr.msleague.msgui.server
import org.apache.commons.codec.binary.Base64
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Field
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

object SkullManager {

    private val headCachedMap = HashMap<UUID, String>()
    fun getHead(uuid: UUID?): String? = uuid?.run {
        headCachedMap[this]
    }
    fun setHead(uuid: UUID?, tag: String) = uuid?.apply { headCachedMap[this] = tag }

    private val base64Method by lazy { Class.forName("org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64").getMethod("encodeBase64",ByteArray::class.java) }
    private fun base64Func(array: ByteArray): ByteArray { return base64Method.invoke(null,array) as ByteArray }
    private val getMaterialMethod by lazy { Class.forName("org.bukkit.Material").getDeclaredMethod("getMaterial", String::class.java, Boolean::class.java) }
    private fun getMaterialFunc(name: String): Material = getMaterialMethod.invoke(null, name, false) as Material

    fun getSkull(temp: String, amount: Int): ItemStack {
        val head = if(highVersion16 || highVersion17) ItemStack(getMaterialFunc("PLAYER_HEAD"), amount) else ItemStack(Material.SKULL_ITEM, amount, 3.toShort())
        if (temp.isEmpty()) return head
        val url = "https://textures.minecraft.net/texture/$temp"
        val headMeta = head.itemMeta!!
        val profile = GameProfile(UUID.randomUUID(), null)
        val array = String.format("{textures:{SKIN:{url:\"%s\"}}}", url).toByteArray()
        val encodedData = if(highVersion16 || highVersion17) base64Func(array) else Base64.encodeBase64(array)
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

    fun getSkull(temp: UUID, amount: Int): ItemStack {
        val tag = getHead(temp)?: run {
            val s = getURLContents("https://sessionserver.mojang.com/session/minecraft/profile/$temp")
            val g = Gson()
            val obj = g.fromJson(s, JsonObject::class.java)
            val value = obj.getAsJsonArray("properties").get(0).asJsonObject.get("value").asString
            val decoded = String(java.util.Base64.getDecoder().decode(value))
            val newObj = g.fromJson(decoded, JsonObject::class.java)
            val skinURL = newObj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").asString
            val skin = ("{\"textures\":{\"SKIN\":{\"url\":\"$skinURL\"}}}").toByteArray()
            val l = java.util.Base64.getEncoder().encode(skin)
            val hash = l.hashCode().toLong()
            val hashAsId = UUID(hash, hash)
            //server.unsafe.modifyItemStack(tempItem, tag)
            "{SkullOwner:{Id:\"$hashAsId\",Properties:{textures:[{Value:\"$value\"}]}}}"
        }
        setHead(temp, tag)
        return server.unsafe.modifyItemStack(if(highVersion16 || highVersion17) ItemStack(getMaterialFunc("PLAYER_HEAD"), amount) else ItemStack(Material.SKULL_ITEM, amount, 3.toShort()), tag).apply {
            this.amount = amount
        }
    }

    private fun getURLContents(stringURL: String): String {
        val url = URL(stringURL)
        val stringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).use { input ->
            input.readLines().forEach(stringBuilder::append)
        }
        return stringBuilder.toString()
    }
}