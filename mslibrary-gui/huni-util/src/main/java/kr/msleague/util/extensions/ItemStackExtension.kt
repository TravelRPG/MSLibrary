package kr.msleague.util.extensions

import com.google.gson.Gson
import kr.msleague.util.module.SERVER_VERSION_16
import kr.msleague.util.module.SERVER_VERSION_17
import net.minecraft.server.v1_12_R1.NBTTagCompound
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

inline fun <reified T> ItemStack.addNBTTagCompound(data: T): ItemStack {
    return when {
        SERVER_VERSION_16 -> addNBTTagCompound16(data)
        SERVER_VERSION_17 -> addNBTTagCompound17(data)
        else -> CraftItemStack.asBukkitCopy(
            CraftItemStack.asNMSCopy(this)
                .apply {
                    tag = (tag ?: NBTTagCompound())
                        .apply {
                            setString(T::class.simpleName, Gson().toJson(data))
                        }
                }
        )
    }
}

inline fun <reified T> ItemStack.getNBTTagCompound(data: Class<T>): T? {
    return when {
        SERVER_VERSION_16 -> getNBTTagCompound16(data)
        SERVER_VERSION_17 -> getNBTTagCompound17(data)
        else -> {
            val jsonData = CraftItemStack.asNMSCopy(this).tag?.getString(T::class.simpleName) ?: return null
            return Gson().fromJson(jsonData, data)
        }
    }
}