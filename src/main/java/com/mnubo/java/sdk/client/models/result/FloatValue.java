package com.mnubo.java.sdk.client.models.result;

import java.util.UUID;

import org.joda.time.DateTime;

public class FloatValue implements ResultValue  {
    private final float value;

    public FloatValue(float value){
        this.value = value;
    }

    @Override
    public boolean booleanValue() {
        throw new NumberFormatException("Cannot convert a float to boolean");
    }

    @Override
    public int intValue() {
        throw new NumberFormatException("Cannot convert a float to int");
    }

    @Override
    public long longValue() {
        throw new NumberFormatException("Cannot convert a float to long");
    }

    @Override
    public float floatValue() {
        return value;
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
        throw new NumberFormatException("Cannot convert a float to DateTime");
    }

    @Override
    public UUID uuidValue() {
        throw new NumberFormatException("Cannot convert a float to uuid");
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof FloatValue){
            return value == ((FloatValue)obj).floatValue();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Float.valueOf(value).hashCode();
    }
}
