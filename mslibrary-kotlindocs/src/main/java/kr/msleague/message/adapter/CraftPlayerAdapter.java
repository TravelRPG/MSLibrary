package kr.msleague.message.adapter;

import kr.msleague.message.api.MSMessageAdapter;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

/**
 * MSLibrary 에서 기본적으로 제공하는 CraftPlayer 어댑터입니다.
 */
public class CraftPlayerAdapter implements MSMessageAdapter<CraftPlayer> {
    @Override
    public String reformat(String origin, CraftPlayer obj) {
        return "";
    }
}
