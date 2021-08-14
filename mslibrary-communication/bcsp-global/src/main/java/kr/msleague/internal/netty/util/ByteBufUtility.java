package kr.msleague.internal.netty.util;

import io.netty.buffer.ByteBuf;
import kr.msleague.internal.netty.exception.ByteBufException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ByteBufUtility {
    public static void writeString(String s, ByteBuf buf) {
        if (s.length() > 32767)
            throw new ByteBufException(String.format("Cannot send string longer than Short.MAX_VALUE (got %s characters)", new Object[] { Integer.valueOf(s.length()) }));
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        writeVarInt(b.length, buf);
        buf.writeBytes(b);
    }

    public static String readString(ByteBuf buf) {
        int len = readVarInt(buf);
        if (len > 32767)
            throw new ByteBufException(String.format("Cannot receive string longer than Short.MAX_VALUE (got %s characters)", new Object[] { Integer.valueOf(len) }));
        byte[] b = new byte[len];
        buf.readBytes(b);
        return new String(b, Charset.forName("UTF-8"));
    }

    public static void writeArray(byte[] b, ByteBuf buf) {
        if (b.length > 32767)
            throw new ByteBufException(String.format("Cannot send byte array longer than Short.MAX_VALUE (got %s bytes)", new Object[] { Integer.valueOf(b.length) }));
        writeVarInt(b.length, buf);
        buf.writeBytes(b);
    }

    public static byte[] toArray(ByteBuf buf) {
        byte[] ret = new byte[buf.readableBytes()];
        buf.readBytes(ret);
        return ret;
    }

    public static byte[] readArray(ByteBuf buf) {
        return readArray(buf, buf.readableBytes());
    }

    public static byte[] readArray(ByteBuf buf, int limit) {
        int len = readVarInt(buf);
        if (len > limit)
            throw new ByteBufException(String.format("Cannot receive byte array longer than %s (got %s bytes)", new Object[] { Integer.valueOf(limit), Integer.valueOf(len) }));
        byte[] ret = new byte[len];
        buf.readBytes(ret);
        return ret;
    }

    public static int[] readVarIntArray(ByteBuf buf) {
        int len = readVarInt(buf);
        int[] ret = new int[len];
        for (int i = 0; i < len; i++)
            ret[i] = readVarInt(buf);
        return ret;
    }

    public static void writeStringArray(List<String> s, ByteBuf buf) {
        writeVarInt(s.size(), buf);
        for (String str : s)
            writeString(str, buf);
    }

    public static List<String> readStringArray(ByteBuf buf) {
        int len = readVarInt(buf);
        List<String> ret = new ArrayList<>(len);
        for (int i = 0; i < len; i++)
            ret.add(readString(buf));
        return ret;
    }

    public static int readVarInt(ByteBuf input) {
        return readVarInt(input, 5);
    }

    public static int readVarInt(ByteBuf input, int maxBytes) {
        byte in;
        int out = 0;
        int bytes = 0;
        do {
            in = input.readByte();
            out |= (in & Byte.MAX_VALUE) << bytes++ * 7;
            if (bytes > maxBytes)
                throw new RuntimeException("VarInt too big");
        } while ((in & 0x80) == 128);
        return out;
    }

    public static void writeVarInt(int value, ByteBuf output) {
        do {
            int part = value & 0x7F;
            value >>>= 7;
            if (value != 0)
                part |= 0x80;
            output.writeByte(part);
        } while (value != 0);
    }

    public static int readVarShort(ByteBuf buf) {
        int low = buf.readUnsignedShort();
        int high = 0;
        if ((low & 0x8000) != 0) {
            low &= 0x7FFF;
            high = buf.readUnsignedByte();
        }
        return (high & 0xFF) << 15 | low;
    }

    public static void writeVarShort(ByteBuf buf, int toWrite) {
        int low = toWrite & 0x7FFF;
        int high = (toWrite & 0x7F8000) >> 15;
        if (high != 0)
            low |= 0x8000;
        buf.writeShort(low);
        if (high != 0)
            buf.writeByte(high);
    }

    public static void writeUUID(UUID value, ByteBuf output) {
        output.writeLong(value.getMostSignificantBits());
        output.writeLong(value.getLeastSignificantBits());
    }

    public static UUID readUUID(ByteBuf input) {
        return new UUID(input.readLong(), input.readLong());
    }
}
