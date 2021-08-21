package kr.msleague.util.extensions

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location

fun String.toColorString(): String = ChatColor.translateAlternateColorCodes('&', this)
fun String.toStripColorString(): String = ChatColor.stripColor(this.toColorString())
fun String.parseLocation(delimiter: String, deep: Boolean): Location = split(delimiter).run {
    if(deep) Location(Bukkit.getWorld(this[0]), this[1].toDouble(), this[2].toDouble(), this[3].toDouble(), this[4].toFloat(), this[5].toFloat())
    else Location(Bukkit.getWorld(this[0]), this[1].toDouble(), this[2].toDouble(), this[3].toDouble())
}