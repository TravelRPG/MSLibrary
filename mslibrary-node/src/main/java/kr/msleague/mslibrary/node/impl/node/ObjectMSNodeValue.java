package kr.msleague.mslibrary.node.impl.node;


import kr.msleague.mslibrary.node.impl.NumberParser;
import kr.msleague.mslibrary.node.api.MSNodeElement;
import kr.msleague.mslibrary.node.api.MSNodeValue;

public class ObjectMSNodeValue extends MSNodeElementImpl implements MSNodeValue {

    Object value;

    public ObjectMSNodeValue(MSNodeElement parents, String name, Object value) {
        super(parents, name);
        this.value = value;
    }

    public ObjectMSNodeValue(MSNodeElement parents, Object value) {
        super(parents);
        this.value = value;
    }

    @Override
    public boolean getAsBoolean() {
        return value instanceof Boolean ? (boolean) value : Boolean.parseBoolean((String) value);
    }

    @Override
    public byte getAsByte() {
        return value instanceof Number ? getAsNumber().byteValue() : Byte.parseByte((String) value);
    }

    @Override
    public short getAsShort() {
        return value instanceof Number ? getAsNumber().shortValue() : Short.parseShort((String) value);
    }

    @Override
    public int getAsInt() {
        return value instanceof Number ? getAsNumber().intValue() : Integer.parseInt((String) value);
    }

    @Override
    public float getAsFloat() {
        return value instanceof Number ? getAsNumber().floatValue() : Float.parseFloat((String) value);
    }

    @Override
    public double getAsDouble() {
        return value instanceof Number ? getAsNumber().doubleValue() : Double.parseDouble((String) value);
    }

    @Override
    public long getAsLong() {
        return value instanceof Number ? getAsNumber().longValue() : Long.parseLong((String) value);
    }

    @Override
    public String getAsString() {
        if (value instanceof Number) {
            return value.toString();
        } else {
            if (value instanceof Boolean) {
                return Boolean.toString((boolean) value);
            } else
                return (String) value;
        }
    }

    @Override
    public Number getAsNumber() {
        return value instanceof Number ? (Number) value : new NumberParser((String) value);
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

    @Override
    public void set(long l) {

    }

    @Override
    public boolean isNumber() {
        return value instanceof Number;
    }

    @Override
    public boolean isBoolean() {
        return value instanceof Boolean;
    }

    @Override
    public boolean isString() {
        return value instanceof String;
    }

    private void setValue(Object obj) {
        if (obj instanceof Character) {
            this.value = String.valueOf(obj);
            return;
        }
        if (obj instanceof Number || obj instanceof String) {
            this.value = obj;
        }
    }
}
