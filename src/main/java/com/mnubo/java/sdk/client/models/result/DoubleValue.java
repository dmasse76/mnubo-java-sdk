package com.mnubo.java.sdk.client.models.result;

import java.util.UUID;

import org.joda.time.DateTime;

public class DoubleValue implements ResultValue  {
    private final double value;

    public DoubleValue(double value){
        this.value = value;
    }

    @Override
    public boolean booleanValue() {
        throw new NumberFormatException("Cannot convert a double to boolean");
    }

    @Override
    public int intValue() {
        throw new NumberFormatException("Cannot convert a double to int");
    }

    @Override
    public long longValue() {
        throw new NumberFormatException("Cannot convert a double to long");
    }

    @Override
    public float floatValue() {
        throw new NumberFormatException("Cannot convert a double to float");
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public String strValue() {
        return String.valueOf(value);
    }

    @Override
    public DateTime datetimeValue() {
        throw new NumberFormatException("Cannot convert a double to DateTime");
    }

    @Override
    public UUID uuidValue() {
        throw new NumberFormatException("Cannot convert a double to uuid");
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof DoubleValue){
            return value == ((DoubleValue)obj).doubleValue();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }
}
