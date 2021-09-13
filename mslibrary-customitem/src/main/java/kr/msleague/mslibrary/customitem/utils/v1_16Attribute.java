package kr.msleague.mslibrary.customitem.utils;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class v1_16Attribute extends Attribute{

    public v1_16Attribute(String name, String attributeName, double amount, int operation, String slot, Long least, Long most) {
        super(name, attributeName, amount, operation, slot, least, most);
    }

    public v1_16Attribute(String minecraftID, double amount, int operation, String slot, Long least, Long most) {
        super(minecraftID, amount, operation, slot, least, most);

    }

    public v1_16Attribute(String attributeName, int operation, double amount, String slot) {
        super(attributeName, operation, amount, slot);
    }

    public static List<v1_16Attribute> extract(ItemStack item) {
        String name = "Modifier", attributeName, slot;
        double amount;
        int operation;
        long uuidLeast, uuidMost;

        List<v1_16Attribute> result = new ArrayList<>();

        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagList list = compound.hasKey("AttributeModifiers") ? (NBTTagList) compound.get("AttributeModifiers") : new NBTTagList();

        for (int i = 0; list.size() > i; i++) {
            NBTTagCompound modifier = list.getCompound(i);
            attributeName = iDtoName(modifier.getString("AttributeName")); // generic.attackDamage -> GENERIC_ATTACK_DAMAGE
            slot = modifier.getString("Slot");
            amount = modifier.getDouble("Amount");
            operation = modifier.getInt("Operation");
            uuidLeast = modifier.getLong("UUIDLeast");
            uuidMost = modifier.getLong(("UUIDMost"));
            result.add(new v1_16Attribute(name, attributeName, amount, operation, slot, uuidLeast, uuidMost));
        }

        return result;
    }


    public void affect(ItemStack item) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagList list = compound.hasKey("AttributeModifiers") ? (NBTTagList) compound.get("AttributeModifiers") : new NBTTagList();
        NBTTagCompound newCompound = new NBTTagCompound();
        newCompound.set("Name", NBTTagString.a("Modifier"));
        newCompound.set("AttributeName", NBTTagString.a(attributeMinecraftID));
        newCompound.set("Slot", NBTTagString.a(this.slot));
        newCompound.set("Amount", NBTTagDouble.a(this.amount));
        newCompound.set("UUIDLeast", NBTTagLong.a(nextLongMinus()));
        newCompound.set("UUIDMost", NBTTagLong.a(nextLongPlus()));
        list.add(newCompound);
        compound.set("AttributeModifiers", list);
        nmsItem.setTag(compound);

        item.setItemMeta(CraftItemStack.getItemMeta(nmsItem));

    }
}
