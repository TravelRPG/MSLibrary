package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            ItemNode parent = element.asNode();
            ItemMeta meta = target.getItemMeta();
            List<String> lores = new ArrayList<>();
            for(String key : parent.getKeys().stream().sorted().collect(Collectors.toList())){
                ItemElement element1 = parent.get(key);
                if(element1 instanceof ItemNodeValue) {
                    String lore = element1.asValue().getAsString();
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
                ItemNode node = data.getNodes().createNode("minecraft.lore");
                for(int i = 0; i < list.size(); i++){
                    node.setPrimitive(i+"", list.get(i));
                }
            }
        }
        return data;
    }
}
