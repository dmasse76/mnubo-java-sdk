package com.mnubo.java.sdk.client.mapper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.mnubo.java.sdk.client.models.result.SearchResult;

public class SearchResultDeserializerTest extends AbstractSerializerTest {

    @Test
    public void testDeserialize() throws Exception {
        String json = "{\"columns\":[{\"label\":\"speed\",\"type\":\"DOUBLE\"},{\"label\":\"state\",\"type\":\"TEXT\"},{\"label\":\"isActive\",\"type\":\"BOOLEAN\"}],\"rows\":[[91.0,\"aaa\",true],[88.23,\"bbb\",true],[87893.2333,\"ccc\",false]]}";
        List<SearchResult.Column> colums = new ArrayList<>();
        colums.add(new SearchResult.Column("speed", "DOUBLE"));
        colums.add(new SearchResult.Column("state", "TEXT"));
        colums.add(new SearchResult.Column("isActive", "BOOLEAN"));

        List<List<Object>> rows = new ArrayList<>();
        rows.add(Arrays.asList((Object)91.0,"aaa",true));
        rows.add(Arrays.asList((Object)88.23,"bbb",true));
        rows.add(Arrays.asList((Object)87893.2333,"ccc",false));
        SearchResult expected = new SearchResult(colums, rows);

        SearchResult result = mapper.readValue(json, SearchResult.class);

        assertThat(result, equalTo(expected));
    }


}
