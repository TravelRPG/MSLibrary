package kr.msleague.util.module

import org.bukkit.plugin.java.JavaPlugin

internal lateinit var plugin: JavaPlugin
internal val server by lazy { plugin.server }
val SERVER_VERSION_16: Boolean by lazy { server.version.contains("1.16") }
val SERVER_VERSION_17: Boolean by lazy { server.version.contains("1.19") }
