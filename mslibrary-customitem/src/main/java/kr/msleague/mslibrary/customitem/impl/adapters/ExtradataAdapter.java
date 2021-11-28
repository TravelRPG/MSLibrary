package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class ExtradataAdapter implements ItemAdapter<ItemStack> {
    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        return null;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        return null;
    }
}
