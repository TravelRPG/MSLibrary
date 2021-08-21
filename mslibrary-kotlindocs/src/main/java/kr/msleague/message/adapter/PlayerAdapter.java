package kr.msleague.message.adapter;

import kr.msleague.message.api.MSMessageAdapter;
import org.bukkit.entity.Player;

/**
 * MSLibrary 에서 기본적으로 제공하는 Player 어댑터입니다.
 */
public class PlayerAdapter implements MSMessageAdapter<Player> {
    @Override
    public String reformat(String origin, Player obj) {
        return "";
    }
}
