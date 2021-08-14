package kr.msleague.bcsp.internal.logger;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JavaUtilLogger implements Logger{
    private java.util.logging.Logger logger;
    @Override
    public void info(String message, Object... rep) {
        for(int i = 0; i < rep.length; i++){
            message = message.replace("{"+i+"}", rep[i].toString());
        }
       logger.info(message);
    }

    @Override
    public void warn(String message, Object... rep) {
        for(int i = 0; i < rep.length; i++){
            message = message.replace("{"+i+"}", rep[i].toString());
        }
        logger.warning(message);
    }

    @Override
    public void err(String message, Object... rep) {
        for(int i = 0; i < rep.length; i++){
            message = message.replace("{"+i+"}", rep[i].toString());
        }
        logger.severe(message);
    }
}
