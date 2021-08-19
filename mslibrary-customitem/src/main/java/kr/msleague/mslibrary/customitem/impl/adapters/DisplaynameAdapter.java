package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * Minecraft Item Displayname adapter implementation.
 * @since 1.0
 * @author Arkarang
 */
public class DisplaynameAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement element = data.getNodes().get("minecraft.displayname");
        if(element != null) {
            String name = element.asValue().getAsString();
            ItemMeta meta = target.getItemMeta();
            meta.setDisplayName(name.replaceAll("&", "§"));
            target.setItemMeta(meta);
        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        if(target.getItemMeta() != null) {
            String display = target.getItemMeta().getDisplayName();
            if(display != null) {
                data.getNodes().setPrimitive("minecraft.displayname", display);
            }
        }
        return data;
    }
}
