package kr.msleague.mslibrary.customitem.utils.nms;

import java.lang.reflect.Method;

public class CustomNBTTagList {

    private static Class<?> nbtTagListClass;
    private static Method size;
    private static Method get;
    private static Method add;

    static {
        try {
            nbtTagListClass = Class.forName("net.minecraft.server.v1_12_R1.NBTTagList");
            Class<?> nbtBase = Class.forName("net.minecraft.server.v1_12_R1.NBTBase");
            size = nbtTagListClass.getDeclaredMethod("size");
            get = nbtTagListClass.getDeclaredMethod("get", Integer.class);
            add = nbtTagListClass.getDeclaredMethod("add", nbtBase);
        } catch (Exception ignored) {}
    }

    Object nbtTagList;

    public CustomNBTTagList(Object object) {
        nbtTagList = object;
    }

    public int size() {
        try {
            return (int) size.invoke(nbtTagList);
        } catch (Exception e) {
            return 0;
        }
    }

    public CustomNBTTagCompound get(int i) {
        try {
            return new CustomNBTTagCompound(get.invoke(nbtTagList, i));
        } catch (Exception e) {
            return null;
        }
    }

    public void add(CustomNBTTagCompound com) {
        try {
            add.invoke(nbtTagList, com.nbtTagCompound);
        } catch (Exception ignored) {}
    }

}
