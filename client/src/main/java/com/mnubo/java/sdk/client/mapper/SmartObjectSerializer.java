/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Mnubo Inc. The program(s) may be used and/or copied
 * only with the written permission from Mnubo Inc.
 * or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias
 * Date  : Sep 9, 2015
 *
 * ---------------------------------------------------------------------------
 */
package com.mnubo.java.sdk.client.mapper;

import static com.mnubo.java.sdk.client.models.SmartObject.DEVICE_ID;
import static com.mnubo.java.sdk.client.models.SmartObject.OBJECT_TYPE;
import static com.mnubo.java.sdk.client.models.SmartObject.OWNER;
import static com.mnubo.java.sdk.client.models.SmartObject.REGISTRATION_DATE;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mnubo.java.sdk.client.models.SmartObject;

public class SmartObjectSerializer extends StdSerializer<SmartObject> {
    public SmartObjectSerializer() {
        super(SmartObject.class);
    }

    @Override
    public void serialize(SmartObject value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonGenerationException {
        jgen.writeStartObject();
        if (value.getRegistrationDate() != null) {
            jgen.writeStringField(REGISTRATION_DATE, value.getRegistrationDate().toString());
        }
        if (value.getDeviceId() != null) {
            jgen.writeStringField(DEVICE_ID, value.getDeviceId());
        }
        if (value.getObjectType() != null) {
            jgen.writeStringField(OBJECT_TYPE, value.getObjectType());
        }
        if (value.getOwner() != null) {
            jgen.writeObjectField(OWNER, value.getOwner());
        }
        if (value.getAttributes() != null) {
            for (Map.Entry<String, Object> entry : value.getAttributes().entrySet()) {
                jgen.writeObjectField(entry.getKey(), entry.getValue());
            }
        }
        jgen.writeEndObject();
    }

}
