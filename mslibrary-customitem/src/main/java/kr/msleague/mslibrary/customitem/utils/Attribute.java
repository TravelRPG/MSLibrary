package kr.msleague.mslibrary.customitem.utils;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Attribute {

    private static final HashMap<String, String> ATTRIBUTE_NAME = new HashMap<>();

    static {
        ATTRIBUTE_NAME.put("GENERIC_ATTACK_DAMAGE", "generic.attackDamage");
        ATTRIBUTE_NAME.put("GENERIC_ARMOR", "generic.armor");
        ATTRIBUTE_NAME.put("GENERIC_ARMOR_TOUGHNESS", "generic.armorToughness");
        ATTRIBUTE_NAME.put("GENERIC_MOVEMENT_SPEED", "generic.movementSpeed");
        ATTRIBUTE_NAME.put("GENERIC_KNOCKBACK_RESISTANCE", "generic.knockbackResistance");
        ATTRIBUTE_NAME.put("GENERIC_MAX_HEALTH", "generic.maxHealth");
    }

    public String name;
    public String attributeName;
    public String attributeMinecraftID;
    public String slot;
    public double amount;
    public int operation;
    public long uuidLeast, uuidMost;

    public Attribute(String name, String attributeName, double amount, int operation, String slot, Long least, Long most) {
        this.name = name;
        this.attributeName = attributeName;
        this.attributeMinecraftID = nametoID(this.attributeName);
        this.amount = amount;
        this.slot = slot;
        this.operation = operation;
        this.uuidLeast = least;
        this.uuidMost = most;
    }

    public Attribute(String minecraftID, double amount, int operation, String slot, Long least, Long most) {
        this.name = "Modifier";
        this.attributeMinecraftID = minecraftID;
        this.attributeName = iDtoName(minecraftID);
        this.amount = amount;
        this.slot = slot;
        this.operation = operation;
        this.uuidLeast = least;
        this.uuidMost = most;
    }

    public Attribute(String attributeName, int operation, double amount, String slot) {
        this("Modifier", attributeName, amount, operation, slot, nextLongMinus(), nextLongPlus());
    }

    public static List<Attribute> extract(ItemStack item) {
        String name = "Modifier", attributeName, slot;
        double amount;
        int operation;
        long uuidLeast, uuidMost;

        List<Attribute> result = new ArrayList<>();

        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagList list = compound.hasKey("AttributeModifiers") ? (NBTTagList) compound.get("AttributeModifiers") : new NBTTagList();

        for (int i = 0; list.size() > i; i++) {
            NBTTagCompound modifier = list.get(i);
            attributeName = iDtoName(modifier.getString("AttributeName")); // generic.attackDamage -> GENERIC_ATTACK_DAMAGE
            slot = modifier.getString("Slot");
            amount = modifier.getDouble("Amount");
            operation = modifier.getInt("Operation");
            uuidLeast = modifier.getLong("UUIDLeast");
            uuidMost = modifier.getLong(("UUIDMost"));
            result.add(new Attribute(name, attributeName, amount, operation, slot, uuidLeast, uuidMost));
        }

        return result;
    }

    private static Long nextLongPlus() {
        long value = new Random().nextLong();
        return value < 0 ? value * -1 : value;
    }

    private static Long nextLongMinus() {
        long value = new Random().nextLong();
        return value > 0 ? value * -1 : value;
    }

    private static String iDtoName(String id) {
        for (Map.Entry<String, String> entry : ATTRIBUTE_NAME.entrySet()) {
            if (entry.getValue().equals(id)) {
                return entry.getKey();
            }
        }
        return "NoneKey";
    }

    private static String nametoID(String name) {
        return ATTRIBUTE_NAME.getOrDefault(name, "NoneValue");
    }

    public void affect(ItemStack item) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagList list = compound.hasKey("AttributeModifiers") ? (NBTTagList) compound.get("AttributeModifiers") : new NBTTagList();
        NBTTagCompound newCompound = new NBTTagCompound();
        newCompound.set("Name", new NBTTagString("Modifier"));
        newCompound.set("AttributeName", new NBTTagString(attributeMinecraftID));
        newCompound.set("Slot", new NBTTagString(this.slot));
        newCompound.set("Amount", new NBTTagDouble(this.amount));
        newCompound.set("UUIDLeast", new NBTTagLong(nextLongMinus()));
        newCompound.set("UUIDMost", new NBTTagLong(nextLongPlus()));
        list.add(newCompound);
        compound.set("AttributeModifiers", list);
        nmsItem.setTag(compound);

        item.setItemMeta(CraftItemStack.getItemMeta(nmsItem));

    }
}
