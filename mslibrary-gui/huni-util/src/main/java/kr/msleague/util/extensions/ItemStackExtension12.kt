package kr.msleague.util.extensions

import com.google.gson.Gson
import org.bukkit.inventory.ItemStack

private val CraftItemStack: Class<*> = Class.forName("org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack")
val NBTTagCompound12: Class<*> = Class.forName("net.minecraft.server.v1_12_R1.NBTTagCompound")
private val NMSItemStack: Class<*> = Class.forName("net.minecraft.server.v1_12_R1.ItemStack")
private val bukkitCopyMethod = CraftItemStack.getDeclaredMethod("asBukkitCopy", NMSItemStack)
private val nmsCopyMethod = CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack::class.java)
private val setTagMethod = NMSItemStack.getDeclaredMethod("setTag", NBTTagCompound12)
private val getTagMethod = NMSItemStack.getDeclaredMethod("getTag")
private val setStringMethod = NBTTagCompound12.getDeclaredMethod("setString", String::class.java, String::class.java)
private val getStringMethod = NBTTagCompound12.getDeclaredMethod("getString", String::class.java)
val asBukkitCopy12: (Any)->ItemStack = { bukkitCopyMethod.invoke(null, it) as ItemStack }
val asNMSCopy12: (ItemStack)->Any = { nmsCopyMethod.invoke(null, it) }
fun Any.getString12(key: String): String? = getStringMethod.invoke(this, key)?.run { try { this as String } catch (e: Exception) { null } }
fun Any.setString12(key: String, value: String) { setStringMethod.invoke(this, key, value) }
var Any.tag12: Any?
    get() = getTagMethod.invoke(this)
    set(value) { setTagMethod(this, value) }


@Deprecated("걍 버전안적어두댐", ReplaceWith("ItemStack#addNBTTagCompound"))
inline fun <reified T> ItemStack.addNBTTagCompound12(data: T): ItemStack {
    return asBukkitCopy12(asNMSCopy12(this)
            .apply {
                tag12 = (tag12?: NBTTagCompound12.newInstance()).also { it.setString12(T::class.simpleName?: return@apply, Gson().toJson(data)) }
            }
    )
}
@Deprecated("걍 버전안적어두댐", ReplaceWith("ItemStack#addNBTTagCompound"))
inline fun <reified T> ItemStack.getNBTTagCompound12(data: Class<T>): T? {
    val jsonData = asNMSCopy12(this).tag12?.getString12(T::class.simpleName?: return null) ?: return null
    return Gson().fromJson(jsonData, data)
}