package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Minecraft Item Material adapter implementation.
 * @since 1.0
 * @author Arkarang
 */
public class MaterialAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement materialNode = data.getNodes().get("minecraft.material");
        ItemElement dataNode = data.getNodes().get("minecraft.data");
        if(materialNode != null){
            try {
                Material mat = Material.valueOf(materialNode.asValue().getAsString());
                target.setType(mat);
            }catch (IllegalArgumentException ignored){

            }
        }
        if(dataNode != null){
            target.setDurability(dataNode.asValue().getAsShort());
        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        if(target.getType() != Material.AIR)
            data.getNodes().setPrimitive("minecraft.material", target.getType().name());
        if(target.getDurability() != 0){
            data.getNodes().setPrimitive("minecraft.data", target.getDurability());
        }
        return data;
    }

}
