package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * Minecraft Unbreakable adapter implementation.
 *
 * @author Arkarang
 * @since 1.0
 */
public class UnbreakableAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement element = data.getNodes().get("minecraft.unbreakable");
        if (element != null) {
            boolean b = element.asValue().getAsBoolean();
            ItemMeta meta = target.getItemMeta();
            if (meta != null) {
                meta.setUnbreakable(b);
            }
            target.setItemMeta(meta);
        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        ItemMeta meta = target.getItemMeta();
        if (meta.isUnbreakable()) {
            data.getNodes().setPrimitive("minecraft.unbreakable", meta.isUnbreakable());
        }
        return data;
    }
}
