package kr.msleague.bcsp.internal.netty.util;

import lombok.Getter;

public class PingCalculator {
    private int lastIndex = 0;
    private long[] arr = new long[10];
    @Getter
    private long lastPingAverage = -1L;
    @Getter
    private long lastPing = 0;

    public void processPing(long ms) {
        lastPing = ms;
        if (lastIndex < 10) {
            arr[lastIndex++] = ms;
        } else {
            long sum = 0L;
            for (int i = 0; i < 10; i++) {
                sum += arr[i];
            }
            lastPingAverage = sum / 10;
            lastIndex = 0;
            arr[lastIndex++] = ms;
        }
    }
}
