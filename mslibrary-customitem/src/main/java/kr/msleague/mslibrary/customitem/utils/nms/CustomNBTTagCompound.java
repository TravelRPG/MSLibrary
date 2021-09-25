package kr.msleague.mslibrary.customitem.utils.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class CustomNBTTagCompound {

    private static Class<?> nbtTagCompoundClass;
    private static Class<?> nbtTagListClass;
    private static Constructor<?> nbtTagStringConstructor;
    private static Constructor<?> nbtTagDoubleConstructor;
    private static Constructor<?> nbtTagLongConstructor;
    private static Method hasKey;
    private static Method get;
    private static Method getString;
    private static Method getDouble;
    private static Method getInt;
    private static Method getLong;
    private static Method set;

    static {
        try {
            nbtTagListClass = Class.forName("net.minecraft.server.v1_12_R1.NBTTagList");
            nbtTagCompoundClass = Class.forName("net.minecraft.server.v1_12_R1.NBTTagCompound");
            Class<?> nbtTagString = Class.forName("net.minecraft.server.v1_12_R1.NBTTagString");
            Class<?> nbtTagDouble = Class.forName("net.minecraft.server.v1_12_R1.NBTTagDouble");
            Class<?> nbtTagLong = Class.forName("net.minecraft.server.v1_12_R1.NBTTagLong");
            Class<?> nbtBase = Class.forName("net.minecraft.server.v1_12_R1.NBTBase");
            nbtTagStringConstructor = nbtTagString.getConstructor(String.class);
            nbtTagDoubleConstructor = nbtTagDouble.getConstructor(Double.class);
            nbtTagLongConstructor = nbtTagLong.getConstructor(Long.class);
            hasKey = nbtTagListClass.getDeclaredMethod("hasKey", String.class);
            get = nbtTagListClass.getDeclaredMethod("get",String.class);
            getString = nbtTagListClass.getDeclaredMethod("getString", String.class);
            getDouble = nbtTagListClass.getDeclaredMethod("getDouble", String.class);
            getInt = nbtTagListClass.getDeclaredMethod("getInt", Integer.class);
            getLong = nbtTagListClass.getDeclaredMethod("getLong", Long.class);
            set = nbtTagCompoundClass.getDeclaredMethod("set", String.class, nbtBase);
        } catch (Exception ignored) {}
    }
    Object nbtTagCompound;

    public CustomNBTTagCompound() {
        try {
            nbtTagCompound = nbtTagCompoundClass.newInstance();
        } catch (Exception e) {
            nbtTagCompound = null;
        }
    }

    public CustomNBTTagCompound(Object object) {
        nbtTagCompound = object;
    }

    private boolean hasKey(String key) {
        try { return (boolean) hasKey.invoke(nbtTagCompound, key);
        } catch (Exception ignored ) { return false; }
    }

    public CustomNBTTagList get(String key) {
        try {
            if(hasKey(key)) return new CustomNBTTagList(get.invoke(nbtTagCompound, key));
            else return new CustomNBTTagList(nbtTagListClass.newInstance());
        } catch (Exception e) {
            return null;
        }
    }

    public String getString(String key) {
        try {
            return (String) getString.invoke(nbtTagCompound, key);
        } catch (Exception e) {
            return null;
        }
    }

    public double getDouble(String key) {
        try {
            return (double) getDouble.invoke(nbtTagCompound, key);
        } catch (Exception e) {
            return .0;
        }
    }

    public int getInt(String key) {
        try {
            return (int) getInt.invoke(nbtTagCompound, key);
        } catch (Exception e) {
            return 0;
        }
    }

    public long getLong(String key) {
        try {
            return (long) getLong.invoke(nbtTagCompound, key);
        } catch (Exception e) {
            return 0;
        }
    }

    public void setString(String key, String value) {
        try {
            set.invoke(nbtTagCompound, key, nbtTagStringConstructor.newInstance(value));
        } catch (Exception ignored) {}
    }

    public void setDouble(String key, double value) {
        try {
            set.invoke(nbtTagCompound, key, nbtTagDoubleConstructor.newInstance(value));
        } catch (Exception ignored) {}
    }

    public void setLong(String key, long value) {
        try {
            set.invoke(nbtTagCompound, key, nbtTagLongConstructor.newInstance(value));
        } catch (Exception ignored) {}
    }

    public void setList(String key, CustomNBTTagList value) {
        try {
            set.invoke(key, value.nbtTagList);
        } catch (Exception ignored) {}
    }

}
