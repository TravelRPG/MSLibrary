package kr.msleague.message.adapter;

import kr.msleague.message.api.MSMessageAdapter;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

/**
 * MSLibrary 에서 기본적으로 제공하는 CraftItemStack 어댑터입니다.
 */
public class CraftItemStackAdapter implements MSMessageAdapter<CraftItemStack> {
    @Override
    public String reformat(String origin, CraftItemStack obj) {
        return "";
    }
}
