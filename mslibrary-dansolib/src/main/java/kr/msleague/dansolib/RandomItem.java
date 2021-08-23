package kr.msleague.dansolib;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomItem<E> {
    @Getter
    private final HashMap<E, Double> items = new HashMap<>();
    @Getter
    @Setter
    private int max;

    public RandomItem() {
        max = 100;
    }

    public void add(E item, double chance) {
        if (chance <= 0) {
            throw new IllegalArgumentException("Illegal chance " + chance);
        }
        double count = 0;
        for (double val : items.values()) {
            count += val;
        }
        if (count + chance > max) {
            return;
        }
        items.put(item, chance);
    }

    public void remove(E item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public E draw() {
        double random = new Random().nextDouble() * max;
        double count = 0, next = 0;
        E item = null;
        for (Map.Entry<E, Double> entry : items.entrySet()) {
            next = count + entry.getValue();
            if (random >= count && random < next) {
                item = entry.getKey();
                break;
            } else {
                count = next;
            }
        }
        if (item == null) {
            throw new NullPointerException();
        }
        return item;
    }

}
