package kr.msleague.bgmsync.bukkit.event;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;


@Getter
@NoArgsConstructor
public class PlayerSyncReceivedEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private UUID uuid;
    private ByteBuf buffer;
    private String clazz;
    public PlayerSyncReceivedEvent(UUID uuid, String clazz,byte[] buf){
        this.uuid = uuid;
        this.buffer = Unpooled.directBuffer(buf.length);
        this.clazz = clazz;
        this.buffer.writeBytes(buf);
    }
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    public static HandlerList getHandlerList() {
        return handlerList;
    }
    public void release(){
        if(buffer != null)
            buffer.release();
    }
    public boolean isSyncOf(Class<?> type){
        return type.getSimpleName().equals(clazz);
    }
}
