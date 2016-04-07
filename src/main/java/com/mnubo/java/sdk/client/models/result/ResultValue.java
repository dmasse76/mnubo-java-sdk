package com.mnubo.java.sdk.client.models.result;

import java.util.UUID;

import org.joda.time.DateTime;

public interface ResultValue {
    boolean booleanValue();
    int intValue();
    long longValue();
    float floatValue();
    double doubleValue();
    String strValue();
    DateTime datetimeValue();
    UUID uuidValue();
}
