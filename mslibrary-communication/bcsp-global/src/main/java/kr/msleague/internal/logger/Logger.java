package kr.msleague.internal.logger;

public interface Logger {
    void info(String message, Object... rep);
    void warn(String message, Object... rep);
    void err(String message, Object... rep);
}
