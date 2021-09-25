package kr.msleague.mslibrary.misc.itemcomp;

import kr.msleague.mslibrary.misc.Pair;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

public class ItemComparator {
    private LinkedList<ItemStackComparatorFunction> queue = new LinkedList<>();

    public boolean compare(ItemStack key, ItemStack value){
        boolean compareResult = true;
        for (ItemStackComparatorFunction elem : queue) {
            if(!elem.test(new Pair<>(key, value))) {
                compareResult = false;
                break;
            }
        }
        return compareResult;
    }
}
