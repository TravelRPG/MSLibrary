package kr.msleague.inventory.nms;

import com.google.common.base.Preconditions;
import kr.msleague.inventory.CompressAPI;
import kr.msleague.inventory.ItemStackSerializerAdapter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;

public class V1_17_1 implements ItemStackSerializerAdapter {
    @Override
    public byte[] serialize(ItemStack itemStack, boolean compress) {
        Preconditions.checkNotNull(itemStack, "Input item cannot be null");
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(baos)) {
            writeItemStackData(itemStack, dos);
            dos.flush();
            byte[] target = baos.toByteArray();
            return CompressAPI.compress(target, compress);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public ItemStack deserialize(byte[] array) {
        array = CompressAPI.decompress(array);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(array); DataInputStream dis = new DataInputStream(bais); BukkitObjectInputStream bis = new BukkitObjectInputStream(dis)) {
//            net.minecraft.world.item.ItemStack is = new net.minecraft.world.item.ItemStack (CraftMagicNumbers.getItem(Material.getMaterial(dis.readUTF())), dis.readInt());
//            if (dis.readBoolean()) {
//                is.setTag(NBTCompressedStreamTools.a((DataInput) dis));
//            }
            return (ItemStack) bis.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    private void writeItemStackData(ItemStack itemStack, DataOutputStream dos) throws IOException {
//        dos.writeUTF(itemStack.getType().name());
//        dos.writeInt(itemStack.getAmount());
//        dos.writeShort(itemStack.getDurability());
//        NBTTagCompound tc = CraftItemStack.asNMSCopy(itemStack).getTag();
//        dos.writeBoolean(tc != null);
//        if (tc != null) {
//            NBTCompressedStreamTools.a(tc, (DataOutput) dos);
//        }
        BukkitObjectOutputStream output = new BukkitObjectOutputStream(dos);
        output.writeObject(itemStack);
        output.close();
    }
}
