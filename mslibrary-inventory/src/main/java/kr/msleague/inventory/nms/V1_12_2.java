package kr.msleague.inventory.nms;

import com.google.common.base.Preconditions;
import kr.msleague.inventory.CompressAPI;
import kr.msleague.inventory.ItemStackSerializerAdapter;
import net.minecraft.server.v1_12_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.Arrays;

public class V1_12_2 implements ItemStackSerializerAdapter {
    @Override
    public byte[] serialize(ItemStack itemStack, boolean compress) {
        Preconditions.checkNotNull(itemStack, "Input item cannot be null");
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(baos)){
            writeItemStackData(itemStack, dos);
            dos.flush();
            byte[] target = baos.toByteArray();
            return CompressAPI.compress(target, compress);
        }catch(IOException ex){
            return null;
        }
    }

    @Override
    public ItemStack deserialize(byte[] array) {
        array = CompressAPI.decompress(array);
        try(ByteArrayInputStream bais = new ByteArrayInputStream(array); DataInputStream dis = new DataInputStream(bais)){
            net.minecraft.server.v1_12_R1.ItemStack is = new net.minecraft.server.v1_12_R1.ItemStack(CraftMagicNumbers.getItem(Material.getMaterial(dis.readUTF())), dis.readInt(), dis.readShort(), false);
            if(dis.readBoolean()){
                is.setTag(NBTCompressedStreamTools.a(dis));
            }
            return CraftItemStack.asBukkitCopy(is);
        }catch(IOException ex){
            return null;
        }
    }

    private int writeItemStackData(ItemStack itemStack, DataOutputStream dos) throws IOException{
        int initSize = dos.size();
        dos.writeUTF(itemStack.getType().name());
        dos.writeInt(itemStack.getAmount());
        dos.writeShort(itemStack.getDurability());
        NBTTagCompound tc = CraftItemStack.asNMSCopy(itemStack).getTag();
        dos.writeBoolean(tc!=null);
        if(tc!=null){
            NBTCompressedStreamTools.a(tc, (DataOutput) dos);
        }
        return dos.size() - initSize;
    }
}
