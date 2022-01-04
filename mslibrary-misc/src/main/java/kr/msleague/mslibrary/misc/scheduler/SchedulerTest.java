package kr.msleague.mslibrary.misc.scheduler;

public class SchedulerTest {
    public void a() {
        new Scheduler.Builder()
                .waitFor(10L)
                //Synchronization Context
                .run(() -> System.out.println("HH"))
                //Async Context #1
                .newContext().delayedRun(10L, () -> System.out.println("ABC"))
                //Async Context #2
                .newContext().schedule(0L, () -> System.out.println("TEST"))
                //Async Context #1
                .previousContext().waitFor(20L).schedule(0L, () -> System.out.println("ABBC"))
                //Synchronization Context return
                .newInitialContext().run(() -> System.out.println("ABC"))
                //Start scheduler
                .start();
    }
}
