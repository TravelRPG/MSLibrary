package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNodeArray;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Minecraft ItemFlag adapter implementation.
 * @since 1.0
 * @author Arkarang
 */
public class LoreAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement element = data.getNodes().get("minecraft.lore");
        if(element != null) {
            ItemNodeArray array = element.asArray();
            ItemMeta meta = target.getItemMeta();
            List<String> lores = new ArrayList<>();
            for(ItemElement loreNode : array.contents()){
                String lore = loreNode.asValue().getAsString();
                if(lore != null){
                    lores.add(lore.replaceAll("&", "§"));
                }
            }
            meta.setLore(lores);
            target.setItemMeta(meta);
        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        ItemMeta meta = target.getItemMeta();
        if(meta != null){
            List<String> list = meta.getLore();
            if(list != null && !list.isEmpty()){
                data.getNodes().set("minecraft.lore", null);
                ItemNodeArray array = data.getNodes().createArray("minecraft.lore");
                for(String str : list){
                    array.addPrimitive(str);
                }
            }
        }
        return data;
    }
}
