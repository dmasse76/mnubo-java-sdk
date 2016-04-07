package com.mnubo.java.sdk.client.models.result;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.mnubo.java.sdk.client.models.result.ResultSet.ColumnDefinition;

public class SearchRow implements Row {

    private final List<ColumnDefinition> columnDefinitions;
    private final List<ResultValue> rows;

    public SearchRow(List<ColumnDefinition> columnDefinitions, List<ResultValue> rows)
    {
        this.columnDefinitions = columnDefinitions;
        this.rows = rows;
    }

    @Override
    public List<ResultValue> getResultValues() {
        return rows;
    }

    @Override
    public List<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    @Override
    public boolean isNull(int i) {
        return (rows.get(i) == null);
    }

    @Override
    public boolean isNull(String name) {
        return (getRowValueWithColumnName(name) == null);
    }

    @Override
    public String getString(String name) {
        return getRowValueWithColumnName(name).strValue();
    }

    @Override
    public String getString(int i) { return rows.get(i).strValue(); }

    @Override
    public int getInt(String name) {
        return getRowValueWithColumnName(name).intValue();
    }

    @Override
    public int getInt(int i) {
        return rows.get(i).intValue();
    }

    @Override
    public long getLong(String name) {
        return getRowValueWithColumnName(name).longValue();
    }

    @Override
    public long getLong(int i) {
        return rows.get(i).longValue();
    }

    @Override
    public double getDouble(String name) {
        return getRowValueWithColumnName(name).doubleValue();
    }

    @Override
    public double getDouble(int i) {
        return rows.get(i).doubleValue();
    }

    @Override
    public float getFloat(String name) {
        return getRowValueWithColumnName(name).floatValue();
    }

    @Override
    public float getFloat(int i) {
        return rows.get(i).floatValue();
    }

    @Override
    public boolean getBoolean(String name) {
        return getRowValueWithColumnName(name).booleanValue();
    }

    @Override
    public boolean getBoolean(int i) {
        return rows.get(i).booleanValue();
    }

    @Override
    public DateTime getDateTime(String name) {
        return getRowValueWithColumnName(name).datetimeValue();
    }

    @Override
    public DateTime getDateTime(int i) {
        return rows.get(i).datetimeValue();
    }

    @Override
    public UUID getUUID(String name) {
        return getRowValueWithColumnName(name).uuidValue();
    }

    @Override
    public UUID getUUID(int i) {
        return rows.get(i).uuidValue();
    }


    public ResultValue getRowValueWithColumnName(String name) {

        for (int i = 0; i < columnDefinitions.size(); i++) {
            if (columnDefinitions.get(i).getLabel().equalsIgnoreCase(name)) {
                return rows.get(i);
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SearchRow searchRow = (SearchRow) o;

        if (!columnDefinitions.equals(searchRow.columnDefinitions))
            return false;
        return rows.equals(searchRow.rows);

    }

    @Override
    public int hashCode() {
        int result = columnDefinitions.hashCode();
        result = 31 * result + rows.hashCode();
        return result;
    }
}
