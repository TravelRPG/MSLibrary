package kr.msleague.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;

public class InventorySerializer {
    public static byte[] serialize(ItemStackSerializerAdapter adapter, Inventory inventory, boolean compress) {
        Preconditions.checkNotNull(inventory, "Input inventory cannot be null");
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(baos)) {
            int itemSize = 0;
            for (int i = 0; i < inventory.getSize(); i++) {
                if ((inventory.getItem(i)) != null)
                    itemSize++;
            }
            dos.writeInt(inventory.getSize());
            dos.writeInt(itemSize);
            ItemStack temp;
            for (int i = 0; i < inventory.getSize(); i++) {
                if ((temp = inventory.getItem(i)) != null) {
                    dos.writeInt(i);
                    byte[] arr = adapter.serialize(temp, false);
                    dos.writeInt(arr.length);
                    dos.write(arr);
                }
            }
            dos.flush();
            byte[] target = baos.toByteArray();
            return CompressAPI.compress(target, compress);
        } catch (IOException ex) {
            return null;
        }
    }

    public static MInventory deserialize(ItemStackSerializerAdapter adapter, byte[] arr) {
        BukkitInventory dump = new BukkitInventory();
        arr = CompressAPI.decompress(arr);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(arr); DataInputStream dis = new DataInputStream(bais)) {
            dump.setSize(dis.readInt());
            int itemSize = dis.readInt(), slot, arrSize;
            for (int i = 0; i < itemSize; i++) {
                slot = dis.readInt();
                arrSize = dis.readInt();
                byte[] itemArr = new byte[arrSize];
                dis.read(itemArr);
                dump.setItem(slot, adapter.deserialize(itemArr));
            }
            return dump;
        } catch (IOException ex) {
            return null;
        }
    }
}
