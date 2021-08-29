package kr.msleague.teleport.global;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Teleporter {
    @Getter
    @Setter
    private static ITeleporter teleporter;

    public static void teleport(UUID from, UUID to) {
        if (teleporter == null)
            return;
        teleporter.teleport(from, to);
    }

    public static void teleport(UUID who, GLocation to) {
        if (teleporter == null)
            return;
        teleporter.teleport(who, to);
    }

    public static void teleport(String who, String to) {
        if (teleporter == null)
            return;
        teleporter.teleport(who, to);
    }

    public static void teleport(String who, GLocation to) {
        if (teleporter == null)
            return;
        teleporter.teleport(who, to);
    }
}
