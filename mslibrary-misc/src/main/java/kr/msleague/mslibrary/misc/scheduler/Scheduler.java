package kr.msleague.mslibrary.misc.scheduler;

import org.bukkit.Bukkit;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
    private void init0(){
        Bukkit.getScheduler().runTaskTimer(null, this::onTick, 1L ,1L);
    }
    private long currentTick = 0L;
    private SchedulerContext defaultContext = new SchedulerContext();
    LinkedList<SchedulerContext> ctx = new LinkedList<>();
    private void onTick(){
        ctx.forEach(context->{
            context.onTick(currentTick);
        });
        currentTick++;
    }
    public Scheduler(){
        ctx.add(defaultContext);
        defaultContext.setActive(true);
    }

    public static class Builder{
        private Scheduler func = new Scheduler();
        private Deque<SchedulerContext> previousQueue = new LinkedList<>();
        private Deque<SchedulerContext> nextQueue = new LinkedList<>();
        private SchedulerContext currentContext;
        public Builder(){
            currentContext = func.defaultContext;
        }
        public Builder newContext(){
            SchedulerContext ctx = new SchedulerContext();
            currentContext.taskQueue.add(new SchedulatedFunction(0L, 0L, (context)->{
                ctx.setActive(true);
                ctx.setNextHandle(context.getLastProcessedTick() + 1);
            }));
            func.ctx.addLast(ctx);
            previousQueue.add(currentContext);
            currentContext = ctx;
            return this;
        }
        public Builder newInitialContext(){
            SchedulerContext ctx = new SchedulerContext();
            ctx.setActive(true);
            func.ctx.addLast(ctx);
            previousQueue.add(currentContext);
            currentContext = ctx;
            return this;
        }
        public Builder previousContext(){
            if(!previousQueue.isEmpty()){
                nextQueue.add(currentContext);
                currentContext = previousQueue.pop();
            }
            return this;
        }
        public Builder nextContext(){
            if(!nextQueue.isEmpty()){
                previousQueue.add(currentContext);
                currentContext = nextQueue.pop();
            }
            return this;
        }
        public Builder waitFor(long tick){
            currentContext.taskQueue.add(new SchedulatedFunction(0L, tick, x->pass()));
            return this;
        }
        public Builder run(Runnable run){
            currentContext.taskQueue.add(new SchedulatedFunction(0L, 0L, x->run.run()));
            return this;
        }
        public Builder delayedRun(long delay, Runnable run){
            currentContext.taskQueue.add(new SchedulatedFunction(delay, 0L, x->run.run()));
            return this;
        }
        public Builder schedule(long period, Runnable r){
            currentContext.taskQueue.add(new SchedulatedFunction(0L, 0L, x->{
                r.run();
                x.taskQueue.add(new SchedulatedFunction(period, 0L, ex->r.run()));
            }));
            return this;
        }
        private void pass(){}
        public void start(){
            func.init0();
        }
    }
}
