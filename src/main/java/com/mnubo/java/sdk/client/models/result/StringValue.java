package com.mnubo.java.sdk.client.models.result;

import java.util.UUID;

import org.joda.time.DateTime;

public class StringValue implements ResultValue  {
    private final String value;

    public StringValue(String value){
        this.value = value;
    }

    @Override
    public boolean booleanValue() {
        throw new NumberFormatException("Cannot convert a string to boolean");
    }

    @Override
    public int intValue() {
        throw new NumberFormatException("Cannot convert a string to int");
    }

    @Override
    public long longValue() {
        throw new NumberFormatException("Cannot convert a string to long");
    }

    @Override
    public float floatValue() {
        throw new NumberFormatException("Cannot convert a string to float");
    }

    @Override
    public double doubleValue() {
        throw new NumberFormatException("Cannot convert a string to double");
    }

    @Override
    public String strValue() {
        return value;
    }

    @Override
    public DateTime datetimeValue() {
        return DateTime.parse(value);
    }

    @Override
    public UUID uuidValue() {
        return UUID.fromString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof StringValue){
            return value.equals(((StringValue)obj).strValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(value).hashCode();
    }
}
