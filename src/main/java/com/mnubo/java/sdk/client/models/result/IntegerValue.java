package com.mnubo.java.sdk.client.models.result;

import java.util.UUID;

import org.joda.time.DateTime;

public class IntegerValue implements ResultValue  {
    private final int value;

    public IntegerValue(int value){
        this.value = value;
    }

    @Override
    public boolean booleanValue() {
        return (value != 0);
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return (long)value;
    }

    @Override
    public float floatValue() {
        return (float)value;
    }

    @Override
    public double doubleValue() {
        return (double)value;
    }

    @Override
    public String strValue() {
        return String.valueOf(value);
    }

    @Override
    public DateTime datetimeValue() {
        throw new NumberFormatException("Cannot convert a int to DateTime");
    }

    @Override
    public UUID uuidValue() {
        throw new NumberFormatException("Cannot convert a int to uuid");
    }


    @Override
    public boolean equals(Object obj) {
        if( obj instanceof IntegerValue){
            return value == ((IntegerValue)obj).intValue();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(value).hashCode();
    }
}
