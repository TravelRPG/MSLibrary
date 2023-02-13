package kr.msleague.util.extensions

import com.google.gson.Gson
import kr.msleague.util.extensions.ItemStackExtension19.tag19
import net.minecraft.nbt.NBTTagCompound
import org.bukkit.inventory.ItemStack

object ItemStackExtension19 {
    private val CraftItemStack: Class<*> = Class.forName("org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack")
    //val NBTTagCompound19: Class<*> = Class.forName("net.minecraft.nbt.NBTTagCompound")
    private val NMSItemStack: Class<*> = Class.forName("net.minecraft.world.item.ItemStack")
    private val bukkitCopyMethod = CraftItemStack.getDeclaredMethod("asBukkitCopy", NMSItemStack)
    private val nmsCopyMethod = CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack::class.java)
    //private val setTagMethod = NMSItemStack.getDeclaredMethod("setTag", NBTTagCompound19)
    //private val getTagMethod = NMSItemStack.getDeclaredMethod("getTag")
    //private val setStringMethod = NBTTagCompound19.getDeclaredMethod("setString", String::class.java, String::class.java)
    //private val getStringMethod = NBTTagCompound19.getDeclaredMethod("getString", String::class.java)
    val asBukkitCopy19: (Any)->ItemStack = { bukkitCopyMethod.invoke(null, it) as ItemStack }
    val asNMSCopy19: (ItemStack)->net.minecraft.world.item.ItemStack = { nmsCopyMethod.invoke(null, it) as net.minecraft.world.item.ItemStack }
    //fun NBTTagCompound.getString19(key: String): String? = getStringMethod.invoke(this, key)?.run { try { this as String } catch (e: Exception) { null } }
    //fun NBTTagCompound.setString19(key: String, value: String) { setStringMethod.invoke(this, key, value) }
    var net.minecraft.world.item.ItemStack.tag19: NBTTagCompound?
        get() = this.u()
        set(value) {
            c(value)
        }

    inline fun <reified T> ItemStack.addNBTTagCompound19(data: T): ItemStack {
        return asBukkitCopy19(asNMSCopy19(this)
            .apply {
                tag19 = (tag19?: NBTTagCompound()).also {
                    it.a(T::class.simpleName?: return@apply, Gson().toJson(data))
                }
            }
        )
    }

    inline fun <reified T> ItemStack.getNBTTagCompound19(data: Class<T>): T? {
        val jsonData = asNMSCopy19(this).tag19?.l(T::class.simpleName?: return null) ?: return null
        return Gson().fromJson(jsonData, data)
    }
}
