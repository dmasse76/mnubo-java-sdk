/*
 * ---------------------------------------------------------------------------
 * 
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 * 
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 * 
 * Author: marias Date : Jul 22, 2015
 * 
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.spi;

public interface MnuboSDKClient
{
    ObjectsSDK getObjectClient();

    EventsSDK getEventClient();

    OwnersSDK getOwnerClient();

}
