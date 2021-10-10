package kr.msleague.util.extensions

import com.google.gson.Gson
import org.bukkit.inventory.ItemStack

private val CraftItemStack: Class<*> = Class.forName("org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack")
val NBTTagCompound: Class<*> = Class.forName("net.minecraft.server.v1_16_R3.NBTTagCompound")
private val NMSItemStack: Class<*> = Class.forName("net.minecraft.server.v1_16_R3.ItemStack")
private val bukkitCopyMethod = CraftItemStack.getDeclaredMethod("asBukkitCopy", NMSItemStack)
private val nmsCopyMethod = CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack::class.java)
private val setTagMethod = NMSItemStack.getDeclaredMethod("setTag", NBTTagCompound)
private val getTagMethod = NMSItemStack.getDeclaredMethod("getTag")
private val setStringMethod = NBTTagCompound.getDeclaredMethod("setString", String::class.java, String::class.java)
private val getStringMethod = NBTTagCompound.getDeclaredMethod("getString", String::class.java)
val asBukkitCopy16: (Any)->ItemStack = { bukkitCopyMethod.invoke(null, it) as ItemStack }
val asNMSCopy16: (ItemStack)->Any = { nmsCopyMethod.invoke(null, it) }
fun Any.getString(key: String): String? = getStringMethod.invoke(this, key)?.run { try { this as String } catch (e: Exception) { null } }
fun Any.setString(key: String, value: String) { setStringMethod.invoke(this, key, value) }
var Any.tag: Any?
    get() = getTagMethod.invoke(this)
    set(value) { setTagMethod(this, value) }


inline fun <reified T> ItemStack.addNBTTagCompound16(data: T): ItemStack {
    return asBukkitCopy16(asNMSCopy16(this)
            .apply {
                tag = (tag?: NBTTagCompound.newInstance()).also { it.setString(T::class.simpleName?: return@apply, Gson().toJson(data)) }
            }
    )
}

inline fun <reified T> ItemStack.getNBTTagCompound16(data: Class<T>): T? {
    val jsonData = asNMSCopy16(this).tag?.getString(T::class.simpleName?: return null) ?: return null
    return Gson().fromJson(jsonData, data)
}