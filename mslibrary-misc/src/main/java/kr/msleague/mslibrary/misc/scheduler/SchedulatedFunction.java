package kr.msleague.mslibrary.misc.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
@AllArgsConstructor
public class SchedulatedFunction {
    private long preDelay, postDelay;
    private Consumer<SchedulerContext> runnable;
}
