package kr.msleague.teleport.global;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GLocation {
    private String serverName;
    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    private GLocation() {
    }

    public GLocation(String serverName, String worldName, double x, double y, double z) {
        this.serverName = serverName;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public static GLocation read(ByteBuf buf) {
        GLocation loc = new GLocation();
        loc.serverName = ByteBufUtility.readString(buf);
        loc.worldName = ByteBufUtility.readString(buf);
        loc.x = buf.readDouble();
        loc.y = buf.readDouble();
        loc.z = buf.readDouble();
        loc.yaw = buf.readFloat();
        loc.pitch = buf.readFloat();
        return loc;
    }

    public void writer(ByteBuf buf) {
        ByteBufUtility.writeString(serverName, buf);
        ByteBufUtility.writeString(worldName, buf);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
    }
}
