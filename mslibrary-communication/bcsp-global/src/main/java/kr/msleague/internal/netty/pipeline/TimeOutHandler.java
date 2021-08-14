package kr.msleague.internal.netty.pipeline;

import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

public class TimeOutHandler extends ReadTimeoutHandler {
    public TimeOutHandler(long timeout, TimeUnit unit) {
        super(timeout, unit);
    }
}
