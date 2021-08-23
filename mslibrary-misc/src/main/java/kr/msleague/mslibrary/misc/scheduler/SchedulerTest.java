package kr.msleague.mslibrary.misc.scheduler;

public class SchedulerTest {
    public void a() {
        new Scheduler.Builder()
                .waitFor(10L).run(() -> System.out.println("HH"))
                .newContext().delayedRun(10L, () -> System.out.println("ABC"))
                .newContext().schedule(0L, () -> System.out.println("TEST"))
                .previousContext().waitFor(20L).schedule(0L, () -> System.out.println("ABBC"))
                .newInitialContext().run(() -> System.out.println("ABC"))
                .start();
    }
}
