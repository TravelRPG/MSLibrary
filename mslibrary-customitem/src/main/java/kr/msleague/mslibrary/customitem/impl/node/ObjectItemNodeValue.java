package kr.msleague.mslibrary.customitem.impl.node;

import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNodeValue;

public class ObjectItemNodeValue extends ItemElementImpl implements ItemNodeValue{

    Object value;

    public ObjectItemNodeValue(ItemElement parents, String name, Object value) {
        super(parents, name);
        this.value = value;
    }

    @Override
    public boolean getAsBoolean() {
        return value instanceof Boolean ? (boolean)value : Boolean.parseBoolean((String)value);
    }

    @Override
    public byte getAsByte() {
        return value instanceof Byte ? (byte)value : Byte.parseByte((String)value);
    }

    @Override
    public short getAsShort() {
        return value instanceof Short ? (short)value : Short.parseShort((String)value);
    }

    @Override
    public int getAsInt() {
        return value instanceof Integer ? (int)value : Integer.parseInt((String)value);
    }

    @Override
    public float getAsFloat() {
        return value instanceof Float ? (Float)value : Float.parseFloat((String)value);
    }

    @Override
    public double getAsDouble() {
        return value instanceof Double ? (Double)value : Double.parseDouble((String)value);
    }

    @Override
    public String getAsString() {
        if(value instanceof Number){
            return value.toString();
        }else{
            if(value instanceof Boolean){
                return Boolean.toString((boolean)value);
            }else
                return (String)value;
        }
    }

    @Override
    public void set(byte b) {
        setValue(b);
    }

    @Override
    public void set(short s) {
        setValue(s);
    }

    @Override
    public void set(int i) {
        setValue(i);
    }

    @Override
    public void set(float f) {
        setValue(f);
    }

    @Override
    public void set(double b) {
        setValue(b);
    }

    private void setValue(Object obj){
        if(obj instanceof Character){
            this.value = String.valueOf(obj);
            return;
        }
        if(obj instanceof Number || obj instanceof String){
            this.value = obj;
        }
    }

    @Override
    public String getPath() {
        return parents.getPath();
    }

}
