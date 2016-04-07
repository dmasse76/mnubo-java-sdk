package com.mnubo.java.sdk.client.models.result;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.mnubo.java.sdk.client.models.result.ResultSet.ColumnDefinition;

/**
 * Hold one row of result.
 */
public interface Row {

    List<ColumnDefinition> getColumnDefinitions();

    boolean isNull(int i);
    boolean isNull(String name);

    String getString(String name);
    String getString(int i);
    int getInt(String name);
    int getInt(int i);
    long getLong(String name);
    long getLong(int i);
    double getDouble(String name);
    double getDouble(int i);
    float getFloat(String name);
    float getFloat(int i);
    boolean getBoolean(String name);
    boolean getBoolean(int i);
    DateTime getDateTime(String name);
    DateTime getDateTime(int i);
    UUID getUUID(String name);
    UUID getUUID(int i);

    List<ResultValue> getResultValues();
}