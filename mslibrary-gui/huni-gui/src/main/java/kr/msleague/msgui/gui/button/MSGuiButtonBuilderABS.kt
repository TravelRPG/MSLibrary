package kr.msleague.msgui.gui.button

import com.google.gson.Gson
import com.google.gson.JsonObject
import kr.msleague.msgui.managers.SkullManager
import kr.msleague.msgui.server
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*


abstract class MSGuiButtonBuilderABS {

    var type: MSGuiButtonType = MSGuiButtonType.ITEM_STACK
        internal set
    var material: Material = Material.AIR
        internal set
    var durability: Int = 0
        internal set
    var baseItem: ItemStack? = null
        internal set
    var display: String? = null
        internal set
    var lore: MutableList<String>? = null
        internal set
    var itemFlags: MutableSet<ItemFlag>? = null
        internal set
    var glow: Boolean = false
        internal set
    var action: MSGuiButtonAction? = null
        internal set
    var amount: Int = 1
        internal set
    var cancel: Boolean = false
        internal set
    var cleanable: Boolean = false
        internal set
    var unbreakAble: Boolean = false
        internal set
    var uuid: UUID? = null
        internal set
    private val baseFlags: MutableSet<ItemFlag> get() = itemFlags ?: HashSet<ItemFlag>().apply { itemFlags = this }
    private val baseLore: MutableList<String> get() = lore ?: ArrayList<String>().apply { lore = this }
    internal var owner: OfflinePlayer? = null
    internal var url: String? = null

    fun setDisplayName(displayName: String?) { this.display = displayName }
    fun setLore(lore: List<String>?) {
        if (lore == null || lore.isEmpty()) this.lore = null
        else with(baseLore) {
            if (isNotEmpty()) baseLore.clear()
            baseLore.addAll(lore)
        }
    }
    fun addLore(line: String) {
        if (lore == null) lore = baseItem?.itemMeta?.lore
        baseLore.add(line)
    }
    fun removeLore(index: Int) {
        if (baseItem != null && baseItem!!.hasItemMeta() && baseItem!!.itemMeta.hasLore()) lore =
            baseItem!!.itemMeta.lore
        if (index in 0 until if (lore == null) 0 else lore!!.size) baseLore.removeAt(index)
    }
    fun addItemFlags(vararg itemFlags: ItemFlag) { if (itemFlags.isNotEmpty()) itemFlags.forEach { baseFlags.add(it) } }
    fun setGlow(glow: Boolean) { this.glow = glow }
    fun setAction(action: MSGuiButtonAction) { this.action = action }
    fun setAmount(amount: Int) { this.amount = amount }
    fun setAction(ktFunc: (InventoryClickEvent)->Unit) { this.action = object: MSGuiButtonAction { override fun action(e: InventoryClickEvent) { ktFunc(e) } } }
    fun setCancellable(cancel: Boolean) { this.cancel = cancel }
    fun setUnbreakAble(unbreakAble: Boolean) { this.unbreakAble = unbreakAble }
    fun setCleanable(clean: Boolean) { this.cleanable = clean }
    fun setData(meta: ItemMeta) {
        if (display != null) meta.setDisplayName(display)
        if(lore != null) meta.lore = lore
        if (itemFlags != null) itemFlags?.toTypedArray()?.apply { meta.addItemFlags(*this) }
        if (glow) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        if(unbreakAble) meta.isUnbreakable = true
    }
    internal abstract fun versionBuild(item: ItemStack?): Pair<ItemStack, Boolean>
    fun build(): MSGuiButton {
        var makeLastFunc = true
        val func = versionBuild(if(type==MSGuiButtonType.PLAYER_HEAD) SkullManager.getHead(owner?.uniqueId?: uuid) else null)
        makeLastFunc = func.second
        val item = func.first
        val lastFunc: ((ItemStack?)->Unit)? = if(!makeLastFunc) { item->
            if(item != null) {
                val meta = item.itemMeta as SkullMeta
                val targetUUID = owner?.run {
                    meta.owningPlayer = owner
                    item.itemMeta = meta
                    uniqueId
                }?: uuid.run {
                    try {
                        val s = getURLContents("https://sessionserver.mojang.com/session/minecraft/profile/$this")
                        val g = Gson()
                        val obj = g.fromJson(s, JsonObject::class.java)
                        val value = obj.getAsJsonArray("properties").get(0).asJsonObject.get("value").asString
                        val decoded = String(Base64.getDecoder().decode(value))
                        val newObj = g.fromJson(decoded, JsonObject::class.java)
                        val skinURL = newObj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").asString
                        val skin = ("{\"textures\":{\"SKIN\":{\"url\":\"$skinURL\"}}}").toByteArray()
                        val l = Base64.getEncoder().encode(skin)
                        val hash = l.hashCode().toLong()
                        val hashAsId = UUID(hash, hash)
                        server.unsafe.modifyItemStack(item, "{SkullOwner:{Id:\"$hashAsId\",Properties:{textures:[{Value:\"$value\"}]}}}")
                        this
                    } catch (e: Exception) {
                        null
                    }
                }
                SkullManager.setHead(targetUUID, item.clone())
            }
        } else null
        return MSGuiButton(type, item, lastFunc, action, cancel)
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