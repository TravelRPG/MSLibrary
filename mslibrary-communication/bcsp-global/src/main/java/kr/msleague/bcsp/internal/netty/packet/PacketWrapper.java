package kr.msleague.bcsp.internal.netty.packet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Supplier;

@AllArgsConstructor
@Data
public class PacketWrapper<T extends AbstractPacket> {
    Class<T> clazz;
    Supplier<T> supplier;
}
