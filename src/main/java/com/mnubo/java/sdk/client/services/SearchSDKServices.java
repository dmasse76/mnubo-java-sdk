package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.utils.Convert.toResultValueList;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.validNotNull;

import java.util.ArrayList;
import java.util.List;

import com.mnubo.java.sdk.client.models.DataSet;
import com.mnubo.java.sdk.client.models.result.ResultSet;
import com.mnubo.java.sdk.client.models.result.Row;
import com.mnubo.java.sdk.client.models.result.SearchResult;
import com.mnubo.java.sdk.client.models.result.SearchResult.Column;
import com.mnubo.java.sdk.client.models.result.SearchResultSet;
import com.mnubo.java.sdk.client.models.result.SearchRow;
import com.mnubo.java.sdk.client.spi.SearchSDK;

class SearchSDKServices implements SearchSDK {

    public static final String BASIC_SEARCH_PATH = "/search/basic";
    public static final String DATASETS_SEARCH_PATH = "/search/datasets";

    private final SDKService sdkCommonServices;

    SearchSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    public SDKService getSdkCommonServices() {
        return sdkCommonServices;
    }

    @Override
    public ResultSet search(String query) {
        // url
        final String url = sdkCommonServices.getRestitutionBaseUri().path(BASIC_SEARCH_PATH).build().toString();

        validNotNull(query, "Query body");

        // posting
        SearchResult searchResult = sdkCommonServices.postRequest(url, SearchResult.class, query);

        if(searchResult == null) {
            return null;
        }

        return new SearchResultSet(extractDefinitions(searchResult), extractRows(searchResult));
    }

    @Override
    public List<DataSet> getDatasets() {
        // url
        final String url = sdkCommonServices.getRestitutionBaseUri().path(DATASETS_SEARCH_PATH).build().toString();

        // posting
        return sdkCommonServices.getRequest(url, List.class);
    }

    private static List<ResultSet.ColumnDefinition> extractDefinitions(SearchResult result){
        List<ResultSet.ColumnDefinition> columnDefinitions = new ArrayList<>();
        for( Column column: result.getColumns()) {
            columnDefinitions.add(new ResultSet.ColumnDefinition(column.getLabel(), column.getType()));
        }
        return columnDefinitions;
    }

    private static List<Row> extractRows(SearchResult result){
        List<Row> rows = new ArrayList<>();
        List<ResultSet.ColumnDefinition> columnDefinitions = new ArrayList<>();
        for (Column column : result.getColumns()) {
            columnDefinitions.add(new ResultSet.ColumnDefinition(column.getLabel(), column.getType()));
        }

        for (List<Object> l : result.getRows()) {
            SearchRow searchRow = new SearchRow(columnDefinitions,toResultValueList(l));
            rows.add(searchRow);
        }
        return rows;
    }
}