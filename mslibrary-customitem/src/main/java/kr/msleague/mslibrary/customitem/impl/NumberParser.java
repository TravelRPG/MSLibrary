package kr.msleague.mslibrary.customitem.impl;


public class NumberParser extends Number {
    private String value;

    public NumberParser(String value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return Integer.parseInt(value);
    }

    @Override
    public long longValue() {
        return Long.getLong(value);
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(value);
    }

    @Override
    public double doubleValue() {
        return Double.parseDouble(value);
    }
}
