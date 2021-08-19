package kr.msleague.mslibrary.customitem.impl;

import kr.msleague.mslibrary.customitem.api.MSItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

@RequiredArgsConstructor
public class MSItemStack implements MSItem {

    @Getter
    protected final ItemStack handle;

    @Nullable
    protected net.minecraft.server.v1_12_R1.ItemStack asNMS(){
        if(handle instanceof CraftItemStack)
            try {
                Field field = CraftItemStack.class.getDeclaredField("handle");
                field.setAccessible(true);
                Object f = field.get(handle);
                return (net.minecraft.server.v1_12_R1.ItemStack)f;
            }catch (Exception e){
                return null;
            }
        else
            return null;
    }

    @Override
    public int getID() {
        net.minecraft.server.v1_12_R1.ItemStack nms =  asNMS();
        if(nms != null) {
            NBTTagCompound compound = nms.getTag();
            return compound != null ? compound.getInt("id") : 0;
        }
        return 0;
    }

    @Override
    public long getVersion() {
        net.minecraft.server.v1_12_R1.ItemStack nms =  asNMS();
        if(nms != null) {
            NBTTagCompound compound = nms.getTag();
            return compound != null ? compound.getInt("version") : 0;
        }
        return 0;
    }
}
