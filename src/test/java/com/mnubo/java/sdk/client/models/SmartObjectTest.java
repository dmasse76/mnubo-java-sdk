package com.mnubo.java.sdk.client.models;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class SmartObjectTest {

    @Test
    public void builder() {

        DateTime now = DateTime.now();
        UUID eventId = UUID.randomUUID();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("String", "text");

        SmartObject object = SmartObject
                .builder()
                .withEventId(eventId)
                .withObjectType("type")
                .withRegistrationDate(now)
                .withAttributes(attributes)
                .withDeviceId("deviceId")
                .withOwner("owner")
                .build();

        assertThat(object.getEventId(), is(equalTo(eventId)));
        assertTrue(object.getObjectType().equals("type"));
        assertTrue(object.getDeviceId().equals("deviceId"));
        assertThat(object.getRegistrationDate(), is(equalTo(now.withZone(DateTimeZone.UTC))));
        assertThat(object.getAttributes().size(), equalTo(1));
        assertTrue(object.getAttributes().containsKey("string"));
        assertTrue(object.getAttributes().get("string").equals("text"));
        assertTrue(object.getOwner() != null);
        assertTrue(object.getOwner().getUsername().equals("owner"));
        assertTrue(object.getOwner().getPassword() == null);
        assertTrue(object.getOwner().getRegistrationDate() == null);
        assertTrue(object.getOwner().getAttributes().size() == 0);
        assertTrue(object.getOwner().getEventId() == null);
    }

    @Test
    public void registrationTimeNull() {

        SmartObject object = SmartObject
                .builder()
                .withRegistrationDate(null)
                .build();

        assertTrue(object.getRegistrationDate() == null);
    }

    @Test
    public void addAttributes() {

        SmartObject object = SmartObject
                .builder()
                .withAddedAttribute("String", "text")
                .withAddedAttribute("int", 10)
                .withAddedAttribute("double", 10.7)
                .withAddedAttribute("boolean", true)
                .build();

        assertTrue(object.getEventId() == null);
        assertTrue(object.getDeviceId() == null);
        assertTrue(object.getOwnerUserName() == null);
        assertTrue(object.getOwner() == null);
        assertTrue(object.getObjectId() == null);
        assertTrue(object.getObjectType() == null);
        assertTrue(object.getRegistrationDate() == null);
        assertThat(object.getAttributes().size(), equalTo(4));
        assertTrue(object.getAttributes().containsKey("string"));
        assertThat((String)object.getAttributes().get("string"), is(equalTo("text")));
        assertTrue(object.getAttributes().containsKey("int"));
        assertThat((int)object.getAttributes().get("int"), is(equalTo(10)));
        assertTrue(object.getAttributes().containsKey("double"));
        assertThat((double)object.getAttributes().get("double"), is(equalTo(10.7)));
        assertTrue(object.getAttributes().containsKey("boolean"));
        assertThat((boolean)object.getAttributes().get("boolean"), is(equalTo(true)));
    }

    @Test
    public void attributesCaseSensitive() {

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("String", "text");

        SmartObject object = SmartObject
                .builder()
                .withAttributes(attributes)
                .withAddedAttribute("Int", 10)
                .build();

        assertThat(object.getAttributes().size(), equalTo(2));
        assertTrue(object.getAttributes().containsKey("string"));
        assertTrue(object.getAttributes().get("string").equals("text"));
        assertTrue(object.getAttributes().containsKey("int"));
        assertThat((int)object.getAttributes().get("int"), is(equalTo(10)));
    }

    @Test
    public void AttributesNull() {

        SmartObject object = SmartObject
                .builder()
                .withAttributes(null)
                .build();

        assertThat(object.getAttributes().size(), equalTo(0));
    }

}
