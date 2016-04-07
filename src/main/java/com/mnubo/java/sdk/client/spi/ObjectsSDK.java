package com.mnubo.java.sdk.client.spi;

import com.mnubo.java.sdk.client.models.SmartObject;

/**
 * Event SDK Client. This interface gives access to handle objects.
 */
public interface ObjectsSDK {

    /**
     * Allows create SmartObjects.
     *
     * @param object, SmartObject bean to be created.
     */
    void create(SmartObject object);

    /**
     * Allows update an existing SmartObjects.
     *
     * @param object, SmartObject bean to be updated.
     * @param deviecId, device id to be updated.
     *
     */
    void update(SmartObject object, String deviecId);

    /**
     * Allows delete an existing SmartObjects.
     *
     * @param deviceId, Object's "deviceId" to be deleted.
     */
    void delete(String deviceId);

}
