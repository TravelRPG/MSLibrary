package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Minecraft Item Enchantment adapter implementation.
 * @since 1.0
 * @author Arkarang
 */
public class EnchantAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement element = data.getNodes().get("minecraft.enchant");
        if(element != null) {
            ItemNode node = element.asNode();
            ItemMeta meta = target.getItemMeta();
            for(String key : node.getKeys()){
                ItemElement levelNode = node.get("level");
                if(levelNode != null) {
                    meta.addEnchant(Enchantment.getByName(key), levelNode.asValue().getAsInt(), true);
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
        if(meta.getEnchants() != null && !meta.getEnchants().isEmpty()) {
            ItemElement element = data.getNodes().get("minecraft.enchant");
            if (element == null) {
                element = data.getNodes().createNode("minecraft.enchant");
            }
            ItemNode node = element.asNode();
            for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                String enchant = entry.getKey().getName();
                int lvl = entry.getValue();
                node.setPrimitive(enchant, lvl);
            }
        }
        return data;
    }
}
