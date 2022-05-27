package kr.msleague.msgui

import org.bukkit.Server
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

internal lateinit var plugin: JavaPlugin
internal val server: Server by lazy { plugin.server }
internal val pluginManager: PluginManager by lazy { plugin.server.pluginManager }
internal val highVersion16: Boolean by lazy { server.version.contains("1.16") }
internal val highVersion17: Boolean by lazy { server.version.contains("1.17") }