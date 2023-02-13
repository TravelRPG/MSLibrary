package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class CustomModelDataAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement element = data.getNodes().get("minecraft.customModelData");
        if (element != null) {
            int value = element.asValue().getAsInt();
            if(target.getItemMeta() != null) {
                ItemMeta meta = target.getItemMeta();
                meta.setCustomModelData(value);
                target.setItemMeta(meta);
            }
        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        if (target.getItemMeta() != null) {
            if(target.getItemMeta().hasCustomModelData()){
                data.getNodes().setPrimitive("minecraft.customModelData", target.getItemMeta().getCustomModelData());
            }
        }
        return data;
    }
}
