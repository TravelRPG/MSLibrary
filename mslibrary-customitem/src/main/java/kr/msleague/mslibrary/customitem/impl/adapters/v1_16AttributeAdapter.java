package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import kr.msleague.mslibrary.customitem.utils.v1_16Attribute;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class v1_16AttributeAdapter implements ItemAdapter<ItemStack> {

    @Nonnull
    @Override
    public ItemStack read(@Nonnull ItemStack target, @Nonnull MSItemData data) throws IllegalArgumentException {
        ItemElement element = data.getNodes().get("minecraft.attributes");
        if (element instanceof ItemNode) {
            ItemNode parent = element.asNode();
            for (String key : parent.getKeys()) {
                ItemElement subElement = parent.get(key);
                if (subElement instanceof ItemNode) {
                    ItemNode node = subElement.asNode();
                    ItemElement typeNode = node.get("type");
                    ItemElement slotNode = node.get("slot");
                    ItemElement amountNode = node.get("amount");
                    ItemElement operationNode = node.get("operation");
                    if (typeNode != null && slotNode != null && amountNode != null) {
                        String type = typeNode.asValue().getAsString();
                        String slot = slotNode.asValue().getAsString();
                        double amount = amountNode.asValue().getAsDouble();
                        int operation = operationNode == null ? 0 : operationNode.asValue().getAsInt();
                        new v1_16Attribute(type, operation, amount, slot).affect(target);
                    }
                }
            }

        }
        return target;
    }

    @Nonnull
    @Override
    public MSItemData write(@Nonnull MSItemData data, @Nonnull ItemStack target) {
        if (target instanceof CraftItemStack) {
            CraftItemStack craftItem = (CraftItemStack) target;
            List<v1_16Attribute> list = v1_16Attribute.extract(craftItem);
            if (!list.isEmpty()) {
                ItemNode node;
                ItemElement element = data.getNodes().get("minecraft.attributes");
                if (element == null)
                    element = data.getNodes().createNode("minecraft.attributes");
                node = element.asNode();
                for (int i = 0; i < list.size(); i++) {
                    v1_16Attribute att = list.get(i);
                    ItemNode subNode = node.createNode(i + "");
                    subNode.setPrimitive("type", att.attributeName);
                    subNode.setPrimitive("slot", att.slot == null ? "any" : att.slot);
                    subNode.setPrimitive("amount", att.amount);
                    subNode.setPrimitive("operation", att.operation);
                }
            }
        }
        return data;
    }
}
