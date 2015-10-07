/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Aug 4, 2015
 *
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.utils.ValidationUtils.validIsBlank;

import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.spi.OwnersSDK;

class OwnersSDKServices implements OwnersSDK {

    private static final String OWNER_PATH = "/owners";
    private final SDKService sdkCommonServices;

    OwnersSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    @Override
    public void create(Owner owner) {
        // url
        final String url = sdkCommonServices.getBaseUri().path(OWNER_PATH).build().toString();

        validIsBlank(owner.getUsername(), "usermame cannot be null or empty");

        // posting
        sdkCommonServices.postRequest(url, Owner.class, owner);
    }

    @Override
    public void claim(String username, String deviceId) {
        validIsBlank(username, "usermame cannot be null or empty");
        validIsBlank(deviceId, "deviceId cannot be null or empty");

        // url
        final String url = sdkCommonServices.getBaseUri().path(OWNER_PATH).pathSegment(username, "objects", deviceId, "claim")
                                                         .build().toString();
        // posting
        sdkCommonServices.postRequest(url);
    }

    @Override
    public void update(Owner owner, String username) {
        validIsBlank(username, "usermame cannot be null or empty");

        // url
        final String url = sdkCommonServices.getBaseUri().path(OWNER_PATH).pathSegment(username).build().toString();

        // putting
        sdkCommonServices.putRequest(url, owner);
    }

    @Override
    public void delete(String username) {
        validIsBlank(username, "usermame cannot be null or empty");

        // url
        final String url = sdkCommonServices.getBaseUri().path(OWNER_PATH).pathSegment(username).build().toString();

        // putting
        sdkCommonServices.deleteRequest(url);
    }

}
