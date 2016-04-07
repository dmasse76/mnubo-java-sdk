package com.mnubo.java.sdk.client.models.result;

import java.util.UUID;

import org.joda.time.DateTime;

public class LongValue implements ResultValue  {
    private final long value;

    public LongValue(long value){
        this.value = value;
    }

    @Override
    public boolean booleanValue() {
        return (value != 0);
    }

    @Override
    public int intValue() {
        throw new NumberFormatException("Cannot convert a long to int");
    }

    @Override
    public long longValue() {
        return value;
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
        //assuming the value hold an epoch timestamp
        return new DateTime(value);
    }

    @Override
    public UUID uuidValue() {
        throw new NumberFormatException("Cannot convert a double to uuid");
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof LongValue){
            return value == ((LongValue)obj).longValue();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(value).hashCode();
    }

}
