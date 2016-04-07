package com.mnubo.java.sdk.client.spi;

import java.util.List;

import com.mnubo.java.sdk.client.models.DataSet;
import com.mnubo.java.sdk.client.models.result.ResultSet;

/**
 * Owner SDK Client. This interface gives access to Search APIs.
 */
public interface SearchSDK {

    /**
     * Send a search query using mnubo query language (MQL).
     */
    ResultSet search(String query);

    /**
     * Return the list of datasets that can be searched.
     */
    List<DataSet> getDatasets();

}