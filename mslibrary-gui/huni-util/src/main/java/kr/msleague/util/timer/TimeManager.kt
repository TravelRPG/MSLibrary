package kr.msleague.util.timer

import kr.msleague.util.coroutine.CoroutineTask
import kr.msleague.util.coroutine.SynchronizationContext
import kr.msleague.util.coroutine.schedule
import kr.msleague.util.module.plugin
import kr.msleague.util.timer.annotation.TimeEventHandler
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Method
import java.time.LocalDateTime

object TimeManager {

    private var task: CoroutineTask? = null
    private val listeners: HashMap<JavaPlugin, HashSet<TimeListener>> = HashMap()
    private val calledMethod: HashSet<Method> = HashSet()
    private var lastCalledDay: Int = -1

    private fun stop() {
        task?.cancel()
        task = null
    }

    internal fun run() {
        if (task != null) return
        task = plugin.schedule(SynchronizationContext.ASYNC) {
            while (listeners.isNotEmpty()) {
                val time = LocalDateTime.now()
                if (lastCalledDay != time.dayOfYear) {
                    calledMethod.clear()
                    lastCalledDay = time.dayOfYear
                }
                val mapIterator = listeners.iterator()
                while (mapIterator.hasNext()) {
                    val entry = mapIterator.next()
                    if (!entry.key.isEnabled || entry.value.isEmpty()) {
                        mapIterator.remove()
                        continue
                    }
                    val listenerIterator = entry.value.iterator()
                    while (listenerIterator.hasNext()) {
                        val listener = listenerIterator.next()
                        listener::class.java.declaredMethods.filter {
                            !calledMethod.contains(it) && it.getAnnotation(TimeEventHandler::class.java) != null
                                    && checkEqualsTime(time, it.getAnnotation(TimeEventHandler::class.java))
                        }.apply {
                            entry.key.schedule {
                                forEach {
                                    try {
                                        it.invoke(listener)
                                        calledMethod.add(it)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        listenerIterator.remove()
                                    }
                                }
                            }
                        }
                    }

                }
                waitFor(4)
            }
            stop()
        }
    }

    private fun checkEqualsTime(dateTime: LocalDateTime, handle: TimeEventHandler): Boolean {
        return if (handle.hour < 0 || handle.min < 0 || handle.sec < 0) false
        else handle.hour == dateTime.hour && handle.min == dateTime.minute && handle.sec == dateTime.second
    }

    fun registerTimeListener(plugin: JavaPlugin, vararg timerListener: TimeListener) {
        val set = listeners[plugin] ?: HashSet<TimeListener>().apply { listeners[plugin] = this }
        timerListener.forEach(set::add)
        run()
    }

}