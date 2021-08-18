import kr.msleague.util.coroutine.SynchronizationContext
import kr.msleague.util.coroutine.schedule
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Matchers.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import kotlin.test.assertNotNull

@RunWith(MockitoJUnitRunner::class)
class SchedulerTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var server: Server
    @Mock lateinit var scheduler: BukkitScheduler
    @Mock lateinit var plugin: Plugin

    private var mockRepeatingTask: MockRepeatingTask? = null

    private class MockRepeatingTask(val task: ()->Unit) {
        var cancelled = false
        fun startTask() { while(!cancelled) task() }
    }

    @Before
    fun setup() {
        `when`(scheduler.runTaskTimer(eq(plugin), any(Runnable::class.java), anyLong(), anyLong())).then {
            if(this.mockRepeatingTask != null) throw Exception()
            val task = mock(BukkitTask::class.java)
            val mockRepeatingTask = MockRepeatingTask { (it.arguments[1] as Runnable).run() }
            `when`(task.cancel()).then {
                mockRepeatingTask.cancelled = true
                Unit
            }
            this.mockRepeatingTask = mockRepeatingTask
            task
        }
        `when`(scheduler.runTaskLater(eq(plugin), any(Runnable::class.java), anyLong())).then {
            (it.arguments[1] as Runnable).run()
            mock(BukkitTask::class.java)
        }
        setupServerMock()
        `when`(plugin.server).thenReturn(server)
    }

    @After fun tearDown() { mockRepeatingTask = null }

    @Test
    fun `coroutine scheduler`() {
        scheduler.schedule(plugin) {
            waitFor(10)
            waitFor(10)
            waitFor(50)
        }
        verify(scheduler, times(3)).runTaskLater(eq(plugin), any(Runnable::class.java), anyLong())
        verifyNoMoreInteractions(scheduler)
    }

    @Test
    fun `repeating task`() {
        scheduler.schedule(plugin) {
            repeating(40)
            yield()
            waitFor(40)
        }

        assertNotNull(mockRepeatingTask)
        mockRepeatingTask!!.startTask()
        verify(scheduler,times(1)).runTaskTimer(eq(plugin), any(Runnable::class.java), anyLong(), eq(40L))
        verifyNoMoreInteractions(scheduler)
    }

    @Test
    fun `cancel coroutine scheduler`() {
        var task: BukkitTask? = null
        `when`(scheduler.runTaskLater(eq(plugin), any(Runnable::class.java), anyLong())).then {
            task = mock(BukkitTask::class.java)
            task
        }
        val scheduleTask = scheduler.schedule(plugin) { waitFor(20) }
        scheduleTask.cancel()
        verify(task!!, times(1)).cancel()
    }

    private fun setupServerMock() {
        `when`(server.isPrimaryThread).thenReturn(true)
        `when`(server.scheduler).thenReturn(scheduler)
        if(Bukkit.getServer() == null) Bukkit.setServer(server)
    }

}