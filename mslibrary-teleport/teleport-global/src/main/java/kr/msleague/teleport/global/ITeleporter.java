package kr.msleague.teleport.global;

import java.util.UUID;

public interface ITeleporter {
    void teleport(UUID from, UUID to);

    void teleport(UUID who, GLocation to);

    void teleport(String who, String to);

    void teleport(String who, GLocation to);
}
