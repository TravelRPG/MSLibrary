package kr.msleague.util.extensions

import com.google.gson.Gson
import org.bukkit.inventory.ItemStack

private val CraftItemStack: Class<*> = Class.forName("org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack")
val NBTTagCompound17: Class<*> = Class.forName("net.minecraft.nbt.NBTTagCompound")
private val NMSItemStack: Class<*> = Class.forName("net.minecraft.world.item.ItemStack")
private val bukkitCopyMethod = CraftItemStack.getDeclaredMethod("asBukkitCopy", NMSItemStack)
private val nmsCopyMethod = CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack::class.java)
private val setTagMethod = NMSItemStack.getDeclaredMethod("setTag", NBTTagCompound17)
private val getTagMethod = NMSItemStack.getDeclaredMethod("getTag")
private val setStringMethod = NBTTagCompound17.getDeclaredMethod("setString", String::class.java, String::class.java)
private val getStringMethod = NBTTagCompound17.getDeclaredMethod("getString", String::class.java)
val asBukkitCopy17: (Any)->ItemStack = { bukkitCopyMethod.invoke(null, it) as ItemStack }
val asNMSCopy17: (ItemStack)->Any = { nmsCopyMethod.invoke(null, it) }
fun Any.getString17(key: String): String? = getStringMethod.invoke(this, key)?.run { try { this as String } catch (e: Exception) { null } }
fun Any.setString17(key: String, value: String) { setStringMethod.invoke(this, key, value) }
var Any.tag17: Any?
    get() = getTagMethod.invoke(this)
    set(value) { setTagMethod(this, value) }

@Deprecated("걍 버전안적어두댐", ReplaceWith("ItemStack#addNBTTagCompound"))
inline fun <reified T> ItemStack.addNBTTagCompound17(data: T): ItemStack {
    return asBukkitCopy17(asNMSCopy17(this)
            .apply {
                tag17 = (tag17?: NBTTagCompound17.newInstance()).also { it.setString17(T::class.simpleName?: return@apply, Gson().toJson(data)) }
            }
    )
}
@Deprecated("걍 버전안적어두댐", ReplaceWith("ItemStack#addNBTTagCompound"))
inline fun <reified T> ItemStack.getNBTTagCompound17(data: Class<T>): T? {
    val jsonData = asNMSCopy17(this).tag17?.getString17(T::class.simpleName?: return null) ?: return null
    return Gson().fromJson(jsonData, data)
}