package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * Minecraft ItemFlag adapter implementation.
 * @since 1.0
 * @author Arkarang
 */
public class ItemFlagAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement element = data.getNodes().get("minecraft.itemflags");
        if(element != null) {
            ItemNodeArray array = element.asArray();
            ItemMeta meta = target.getItemMeta();
            for(ItemElement flagNode : array.contents()){
                String name = flagNode.asValue().getAsString();
                if(name != null){
                    ItemFlag flag = ItemFlag.valueOf(name);
                    meta.addItemFlags(flag);
                }
            }
            target.setItemMeta(meta);
        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        ItemMeta meta = target.getItemMeta();
        if(meta != null) {
            data.getNodes().set("minecraft.itemflags", null);
            ItemNodeArray array = data.getNodes().createArray("minecraft.itemflags");
            for (ItemFlag flag : meta.getItemFlags()) {
                array.addPrimitive(flag.name());
            }
        }
        return data;
    }
}
