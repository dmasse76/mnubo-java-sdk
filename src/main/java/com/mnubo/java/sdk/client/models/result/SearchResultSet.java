package com.mnubo.java.sdk.client.models.result;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class SearchResultSet implements ResultSet {

    private final List<ColumnDefinition> columnsMetadata;
    private final Queue<Row> queueRows;

    public SearchResultSet(List<ColumnDefinition> columnsMetadata, List<Row> rowValues)
    {
        this.columnsMetadata = columnsMetadata;

        queueRows = new ArrayDeque<>();
        for (Row row : rowValues) {
            queueRows.add(row);
        }
    }

    @Override
    public List<ColumnDefinition> getColumnDefinitions() {
        return columnsMetadata;
    }


    @Override
    public Iterator<Row> iterator() {
        return new Iterator<Row>() {

            @Override
            public boolean hasNext() {
                return !isExhausted();
            }

            @Override
            public Row next() {
                return SearchResultSet.this.one();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Row one() {
        return queueRows.poll();
    }

    @Override
    public boolean isExhausted() {
        return queueRows.isEmpty();
    }

    @Override
    public List<Row> all() {
        if (isExhausted()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(queueRows);
    }
}
