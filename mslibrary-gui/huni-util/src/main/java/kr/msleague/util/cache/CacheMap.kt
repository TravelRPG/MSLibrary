package kr.msleague.util.cache

import kr.msleague.util.coroutine.CoroutineTask
import kr.msleague.util.coroutine.SynchronizationContext
import kr.msleague.util.coroutine.schedule
import org.bukkit.plugin.java.JavaPlugin

class CacheMap<K, V>(private val plugin: JavaPlugin, private val liveTick: Long = 600) {

    private val map = HashMap<K,CacheData<V>>()

    fun put(key: K, value: V) = map.apply {
        get(key)?.cancel()
        this[key] = CacheData(key, value)
    }
    fun get(key: K): V? = map[key]?.data
    fun getValue(key: K): V? = map[key]?.data
    fun contains(key: K): Boolean = map.containsKey(key)
    fun remove(key: K): V? = map.remove(key)?.run {
        cancel()
        data
    }
    fun clear() = map.forEach { (t, _) -> remove(t) }
    fun forEach(block: (key: K, value: V)->Unit) = map.forEach { block(it.key, it.value.data) }
    val keys: Set<K> get() = map.keys

    inner class CacheData<V>(private val key: K, val data: V) {

        private val routine: CoroutineTask = plugin.server.scheduler.schedule(plugin, SynchronizationContext.ASYNC) {
            waitFor(liveTick)
            map.remove(key)
        }

        fun cancel() = routine.cancel()
    }
}

operator fun <K,V> CacheMap<K, V>.get(key: K): V? = getValue(key)
operator fun <K,V> CacheMap<K, V>.set(key: K, value: V) = put(key, value)