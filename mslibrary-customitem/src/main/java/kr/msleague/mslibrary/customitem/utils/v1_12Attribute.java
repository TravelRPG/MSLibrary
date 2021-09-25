package kr.msleague.mslibrary.customitem.utils;

import kr.msleague.mslibrary.customitem.utils.nms.CustomNBTTagCompound;
import kr.msleague.mslibrary.customitem.utils.nms.CustomNBTTagList;
import kr.msleague.mslibrary.customitem.utils.nms.CustomNMSItem;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class v1_12Attribute extends Attribute{

    public v1_12Attribute(String name, String attributeName, double amount, int operation, String slot, Long least, Long most) {
        super(name, attributeName, amount, operation, slot, least, most);
    }

    public v1_12Attribute(String minecraftID, double amount, int operation, String slot, Long least, Long most) {
        super(minecraftID, amount, operation, slot, least, most);

    }

    public v1_12Attribute(String attributeName, int operation, double amount, String slot) {
        super(attributeName, operation, amount, slot);
    }

    public static List<v1_12Attribute> extract(ItemStack item) {
        String name = "Modifier", attributeName, slot;
        double amount;
        int operation;
        long uuidLeast, uuidMost;

        List<v1_12Attribute> result = new ArrayList<>();


        CustomNMSItem nmsItem = new CustomNMSItem(item);
        CustomNBTTagCompound compound = nmsItem.getTag();
        CustomNBTTagList list = compound.get("AttributeModifiers");

        for (int i = 0; list.size() > i; i++) {
            CustomNBTTagCompound modifier = list.get(i);
            attributeName = iDtoName(modifier.getString("AttributeName")); // generic.attackDamage -> GENERIC_ATTACK_DAMAGE
            slot = modifier.getString("Slot");
            amount = modifier.getDouble("Amount");
            operation = modifier.getInt("Operation");
            uuidLeast = modifier.getLong("UUIDLeast");
            uuidMost = modifier.getLong(("UUIDMost"));
            result.add(new v1_12Attribute(name, attributeName, amount, operation, slot, uuidLeast, uuidMost));
        }

        return result;
    }


    public void affect(ItemStack item) {
        CustomNMSItem nmsItem = new CustomNMSItem(item);
        CustomNBTTagCompound compound = nmsItem.getTag();
        CustomNBTTagList list = compound.get("AttributeModifiers");
        CustomNBTTagCompound newCompound = new CustomNBTTagCompound();
        newCompound.setString("Name", "Modifier");
        newCompound.setString("AttributeName", attributeMinecraftID);
        newCompound.setString("Slot", this.slot);
        newCompound.setDouble("Amount", this.amount);
        newCompound.setLong("UUIDLeast", nextLongMinus());
        newCompound.setLong("UUIDMost", nextLongPlus());
        list.add(newCompound);
        compound.setList("AttributeModifiers", list);
        nmsItem.setTag(compound);

        item.setItemMeta(nmsItem.getItemMeta());
    }
}
