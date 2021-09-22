package kr.msleague.mslibrary.misc.itemcomp;

import kr.msleague.mslibrary.misc.Pair;
import org.bukkit.inventory.ItemStack;
import java.util.function.Predicate;

public interface ItemStackComparatorFunction extends Predicate<Pair<ItemStack, ItemStack>> {
}
