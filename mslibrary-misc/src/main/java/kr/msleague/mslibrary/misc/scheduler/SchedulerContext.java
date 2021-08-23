package kr.msleague.mslibrary.misc.scheduler;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@Setter
public class SchedulerContext {
    protected Queue<SchedulatedFunction> taskQueue = new LinkedList<>();
    private boolean active = false;
    private long startTick = 0L, nextHandle = 0L;
    private SchedulatedFunction currentFunction;
    private FunctionPhase phase = FunctionPhase.PRE;
    private long lastProcessedTick;

    public void onTick(long currentTick) {
        if (currentTick == nextHandle && active) {
            lastProcessedTick = currentTick;
            if (phase == FunctionPhase.POST || currentFunction == null) {
                nextHandle += currentFunction != null ? currentFunction.getPostDelay() : 1;
                if (!taskQueue.isEmpty()) {
                    currentFunction = taskQueue.poll();
                    phase = FunctionPhase.PRE;
                }
            } else if (phase == FunctionPhase.PRE) {
                nextHandle += currentFunction.getPreDelay();
                phase = FunctionPhase.RUN;
            } else {
                currentFunction.getRunnable().accept(this);
                phase = FunctionPhase.POST;
                nextHandle += 1;
            }
        }
    }
}
