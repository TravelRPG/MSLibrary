package kr.msleague.util.coroutine

import kr.msleague.util.coroutine.SynchronizationContext.*
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import java.lang.IllegalStateException
import kotlin.coroutines.*

fun Plugin.schedule(initialContext: SynchronizationContext = SYNC,
    co: suspend BukkitSchedulerController.() -> Unit): CoroutineTask =
    server.scheduler.schedule(this, initialContext, co)

fun BukkitScheduler.schedule(plugin: Plugin, initialContext: SynchronizationContext = SYNC,
    co: suspend BukkitSchedulerController.()->Unit): CoroutineTask {
    val controller = BukkitSchedulerController(plugin, this)
    val block: suspend BukkitSchedulerController.() -> Unit = {
        try {
            start(initialContext)
            co()
        } finally { cleanup() }
    }
    block.createCoroutine(controller, controller).resume(Unit)
    return CoroutineTask(controller)
}

@RestrictsSuspension
class BukkitSchedulerController(val plugin: Plugin, val scheduler: BukkitScheduler): Continuation<Unit> {
    override val context: CoroutineContext get() = EmptyCoroutineContext
    private var schedulerDelegate: TaskScheduler = NonRepeatingTaskScheduler(plugin , scheduler)
    val currentTask: BukkitTask?  get() = schedulerDelegate.currentTask
    val isRepeating: Boolean get() = schedulerDelegate is RepeatingTaskScheduler
    internal suspend fun start(initialContext: SynchronizationContext) = suspendCoroutine<Unit> { cont ->
        schedulerDelegate.doContextSwitch(initialContext) { cont.resume(Unit) }
    }
    internal fun cleanup() { currentTask?.cancel() }
    override fun resumeWith(result: Result<Unit>) {
        cleanup()
        result.getOrThrow()
    }
    suspend fun waitFor(ticks: Long): Long = suspendCoroutine { cont-> schedulerDelegate.doWait(ticks, cont::resume) }
    suspend fun yield(): Long = suspendCoroutine { cont -> schedulerDelegate.doYield(cont::resume) }
    suspend fun switchContext(context: SynchronizationContext): Boolean = suspendCoroutine { cont -> schedulerDelegate.doContextSwitch(context, cont::resume) }
    suspend fun newContext(context: SynchronizationContext): Unit = suspendCoroutine { cont-> schedulerDelegate.forceNewContext(context, { cont.resume(Unit) }) }
    suspend fun repeating(resolution: Long): Long = suspendCoroutine { cont ->
        schedulerDelegate = RepeatingTaskScheduler(resolution, plugin, scheduler)
        schedulerDelegate.forceNewContext(currentContext()) { cont.resume(0) }
    }
}

class CoroutineTask internal constructor(private val controller: BukkitSchedulerController) {
    val plugin: Plugin get() = controller.plugin
    val currentTask: BukkitTask? get() = controller.currentTask
    val isSync: Boolean get() = controller.currentTask?.isSync?: false
    val isAsync: Boolean get() = !(controller.currentTask?.isSync?: true)
    fun cancel() { controller.resume(Unit) }
}

enum class SynchronizationContext { SYNC, ASYNC }
private class RepetitionContinuation(val resume: (Long)->Unit, val delay: Long = 0) {
    var passedTicks = 0L
    private var resumed = false
    fun tryResume(passedTicks: Long) {
        if(resumed) throw IllegalStateException()
        this.passedTicks += passedTicks
        if(this.passedTicks >= delay) {
            resumed = true
            resume(this.passedTicks)
        }
    }
}

private interface TaskScheduler {
    val currentTask: BukkitTask?
    fun doWait(ticks: Long, task: (Long)->Unit)
    fun doYield(task: (Long)->Unit)
    fun doContextSwitch(context: SynchronizationContext, task: (Boolean)->Unit)
    fun forceNewContext(context: SynchronizationContext, task: ()->Unit)
}
private class NonRepeatingTaskScheduler(val plugin: Plugin, val scheduler: BukkitScheduler): TaskScheduler {

    override var currentTask: BukkitTask? = null
    override fun doWait(ticks: Long, task: (Long) -> Unit) { runTaskLater(ticks) { task(ticks) } }
    override fun doYield(task: (Long) -> Unit) { doWait(0, task) }
    override fun doContextSwitch(context: SynchronizationContext, task: (Boolean) -> Unit) {
        val currentContext = currentContext()
        if(context == currentContext) task(false)
        else forceNewContext(context) { task(true) }
    }
    override fun forceNewContext(context: SynchronizationContext, task: () -> Unit) { runTask(context) { task() } }
    private fun runTask(context: SynchronizationContext = currentContext(), task: ()->Unit) {
        currentTask = when(context) {
            SYNC->scheduler.runTask(plugin, task)
            ASYNC->scheduler.runTaskAsynchronously(plugin, task)
        }
    }
    private fun runTaskLater(ticks: Long, context: SynchronizationContext = currentContext(), task:()->Unit) {
        currentTask = when(context) {
            SYNC->scheduler.runTaskLater(plugin, task, ticks)
            ASYNC->scheduler.runTaskLaterAsynchronously(plugin, task, ticks)
        }
    }

}

private class RepeatingTaskScheduler(
    val interval: Long,
    val plugin: Plugin,
    val scheduler: BukkitScheduler
): TaskScheduler {

    override var currentTask: BukkitTask? = null
    private var nextContinuation: RepetitionContinuation? = null
    override fun doWait(ticks: Long, task: (Long)->Unit) { nextContinuation = RepetitionContinuation(task, ticks) }
    override fun doYield(task: (Long)->Unit) { nextContinuation = RepetitionContinuation(task) }

    override fun doContextSwitch(context: SynchronizationContext, task: (Boolean) -> Unit) {
        val currentContext = currentContext()
        if(context == currentContext) task(false)
        else forceNewContext(context) { task(true) }
    }

    override fun forceNewContext(context: SynchronizationContext, task: () -> Unit) {
        doYield { task() }
        runTaskTimer(context)
    }

    private fun runTaskTimer(context: SynchronizationContext) {
        currentTask?.cancel()
        val task: ()-> Unit = { nextContinuation?.tryResume(interval) }
        currentTask = when(context) {
            SYNC->scheduler.runTaskTimer(plugin, task, 0L, interval)
            ASYNC->scheduler.runTaskTimerAsynchronously(plugin, task, 0L, interval)
        }
    }

}
private fun currentContext() = if(Bukkit.isPrimaryThread()) SYNC else ASYNC