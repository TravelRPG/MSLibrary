package kr.msleague.msgui

import org.bukkit.Server
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: JavaPlugin
val server: Server by lazy { plugin.server }
val pluginManager: PluginManager by lazy { plugin.server.pluginManager }