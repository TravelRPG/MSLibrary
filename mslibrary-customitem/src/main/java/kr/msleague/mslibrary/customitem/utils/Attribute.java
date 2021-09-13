package kr.msleague.mslibrary.customitem.utils;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Attribute {

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

    protected static Long nextLongPlus() {
        long value = new Random().nextLong();
        return value < 0 ? value * -1 : value;
    }

    protected static Long nextLongMinus() {
        long value = new Random().nextLong();
        return value > 0 ? value * -1 : value;
    }

    protected static String iDtoName(String id) {
        for (Map.Entry<String, String> entry : ATTRIBUTE_NAME.entrySet()) {
            if (entry.getValue().equals(id)) {
                return entry.getKey();
            }
        }
        return "NoneKey";
    }

    protected static String nametoID(String name) {
        return ATTRIBUTE_NAME.getOrDefault(name, "NoneValue");
    }

    public abstract void affect(ItemStack item);

}
