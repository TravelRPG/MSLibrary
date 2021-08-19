package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.*;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.utils.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Minecraft Item Attribute adapter implementation.
 * @since 1.0
 * @author Arkarang
 */
public class AttributeAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement element = data.getNodes().get("minecraft.attributes");
        if(element != null) {
            ItemNodeArray array = element.asArray();
            for (ItemElement content : array.contents()) {
                ItemNode node = content.asNode();
                ItemElement typeNode = node.get("type");
                ItemElement slotNode = node.get("slot");
                ItemElement amountNode = node.get("amount");
                ItemElement operationNode = node.get("operation");
                if (typeNode != null && slotNode != null && amountNode != null) {
                    String type = typeNode.asValue().getAsString();
                    String slot = slotNode.asValue().getAsString();
                    double amount = amountNode.asValue().getAsDouble();
                    int operation = operationNode == null ? 0 : operationNode.asValue().getAsInt();
                    new Attribute(type, operation, amount, slot).affect(target);
                }
            }
        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        if(target instanceof CraftItemStack){
            CraftItemStack craftItem = (CraftItemStack) target;
            List<Attribute> list = Attribute.extract(craftItem);
            if(!list.isEmpty()) {
                ItemNodeArray array;
                ItemElement element = data.getNodes().get("minecraft.attributes");
                if(element == null)
                    element = data.getNodes().createArray("minecraft.attributes");
                array = element.asArray();
                for(int i = 0 ; i < list.size() ; i++ ){
                    Attribute att = list.get(i);
                    ItemNode node = new HashItemNode(array, i+"");
                    node.setPrimitive("type", att.attributeName);
                    node.setPrimitive("slot", att.slot);
                    node.setPrimitive("amount", att.amount);
                    node.setPrimitive("operation", att.operation);
                }
            }
        }
        return data;
    }
}
