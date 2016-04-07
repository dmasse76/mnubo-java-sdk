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

public class OwnersTest {

    @Test
    public void builder() {

        DateTime now = DateTime.now();
        UUID eventId = UUID.randomUUID();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("String", "text");

        Owner owner = Owner
                .builder()
                .withEventId(eventId)
                .withPassword("password")
                .withRegistrationDate(now)
                .withAttributes(attributes)
                .withUsername("username")
                .build();

        assertThat(owner.getEventId(), is(equalTo(eventId)));
        assertTrue(owner.getPassword().equals("password"));
        assertTrue(owner.getUsername().equals("username"));
        assertThat(owner.getRegistrationDate(), is(equalTo(now.withZone(DateTimeZone.UTC))));
        assertThat(owner.getAttributes().size(), equalTo(1));
        assertTrue(owner.getAttributes().containsKey("string"));
        assertTrue(owner.getAttributes().get("string").equals("text"));
    }

    @Test
    public void registrationTimeNull() {

        Owner owner = Owner
                .builder()
                .withRegistrationDate(null)
                .build();

        assertTrue(owner.getRegistrationDate() == null);
    }

    @Test
    public void addAttributes() {

        Owner owner = Owner
                .builder()
                .withAddedAttribute("String", "text")
                .withAddedAttribute("int", 10)
                .withAddedAttribute("double", 10.7)
                .withAddedAttribute("boolean", true)
                .build();

        assertTrue(owner.getEventId() == null);
        assertTrue(owner.getPassword() == null);
        assertTrue(owner.getUsername() == null);
        assertTrue(owner.getRegistrationDate() == null);
        assertThat(owner.getAttributes().size(), equalTo(4));
        assertTrue(owner.getAttributes().containsKey("string"));
        assertThat((String)owner.getAttributes().get("string"), is(equalTo("text")));
        assertTrue(owner.getAttributes().containsKey("int"));
        assertThat((int)owner.getAttributes().get("int"), is(equalTo(10)));
        assertTrue(owner.getAttributes().containsKey("double"));
        assertThat((double)owner.getAttributes().get("double"), is(equalTo(10.7)));
        assertTrue(owner.getAttributes().containsKey("boolean"));
        assertThat((boolean)owner.getAttributes().get("boolean"), is(equalTo(true)));
    }

    @Test
    public void attributesCaseSensitive() {

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("String", "text");

        Owner owner = Owner
                .builder()
                .withAttributes(attributes)
                .withAddedAttribute("Int", 10)
                .build();

        assertThat(owner.getAttributes().size(), equalTo(2));
        assertTrue(owner.getAttributes().containsKey("string"));
        assertTrue(owner.getAttributes().get("string").equals("text"));
        assertTrue(owner.getAttributes().containsKey("int"));
        assertThat((int)owner.getAttributes().get("int"), is(equalTo(10)));
    }

    @Test
    public void usernameCaseSensitive() {

        Owner owner = Owner
                .builder()
                .withUsername("UserName").build();

        assertTrue(owner.getUsername().equals("username"));
    }

    @Test
    public void AttributesNull() {

        Owner owner = Owner
                .builder()
                .withAttributes(null)
                .build();

        assertThat(owner.getAttributes().size(), equalTo(0));
    }
}
