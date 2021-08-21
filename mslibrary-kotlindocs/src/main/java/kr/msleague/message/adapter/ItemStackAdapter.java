package kr.msleague.message.adapter;

import kr.msleague.message.api.MSMessageAdapter;
import org.bukkit.inventory.ItemStack;

/**
 * MSLibrary 에서 기본적으로 제공하는 ItemStack 어댑터입니다.
 */
public class ItemStackAdapter implements MSMessageAdapter<ItemStack> {
    @Override
    public String reformat(String origin, ItemStack obj) {
        return "";
    }
}
