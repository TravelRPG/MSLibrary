package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Minecraft Item Material adapter implementation.
 *
 * @author Arkarang
 * @since 1.0
 */
public class MaterialAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement materialNode = data.getNodes().get("minecraft.material");
        ItemElement dataNode = data.getNodes().get("minecraft.data");
        if (materialNode != null) {
            try {
                Material mat = Material.valueOf(materialNode.asValue().getAsString());
                target.setType(mat);
            } catch (IllegalArgumentException ignored) {

            }
        }
        if (dataNode != null) {
            target.setDurability((short) dataNode.asValue().getAsInt());
        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        System.out.println("DURA: " + target.getDurability());

        if (target.getType() != Material.AIR)
            data.getNodes().setPrimitive("minecraft.material", target.getType().name());
        if (target.getDurability() != 0) {
            data.getNodes().setPrimitive("minecraft.data", (int) target.getDurability());
        }
        return data;
    }

}
