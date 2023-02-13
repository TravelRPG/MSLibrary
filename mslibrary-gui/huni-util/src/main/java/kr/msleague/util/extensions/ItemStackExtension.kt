package kr.msleague.util.extensions

import com.google.gson.Gson
import kr.msleague.util.extensions.ItemStackExtension19.addNBTTagCompound19
import kr.msleague.util.extensions.ItemStackExtension19.getNBTTagCompound19
import kr.msleague.util.module.SERVER_VERSION_16
import kr.msleague.util.module.SERVER_VERSION_17
//import net.minecraft.server.v1_12_R1.NBTTagCompound
import org.bukkit.Material
//import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

inline fun <reified T> ItemStack.addNBTTagCompound(data: T): ItemStack {
    return when {
        SERVER_VERSION_16 -> addNBTTagCompound16(data)
        SERVER_VERSION_17 -> addNBTTagCompound19(data)
        else -> addNBTTagCompound12(data)
    }
}

inline fun <reified T> ItemStack.getNBTTagCompound(data: Class<T>): T? {
    return when {
        type == Material.AIR -> null
        SERVER_VERSION_16 -> getNBTTagCompound16(data)
        SERVER_VERSION_17 -> getNBTTagCompound19(data)
        else -> getNBTTagCompound12(data)

    }
}