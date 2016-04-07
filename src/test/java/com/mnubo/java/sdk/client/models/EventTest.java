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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EventTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void builder() {

        DateTime now = DateTime.now();
        UUID eventId = UUID.randomUUID();
        Map<String, Object> timeseries = new HashMap<>();
        timeseries.put("String", "text");

        Event event = Event
                .builder()
                .withEventID(eventId)
                .withEventType("type")
                .withSmartObject("deviceId")
                .withTimeseries(timeseries)
                .withTimestamp(now)
                .build();

        assertThat(event.getEventId(), is(equalTo(eventId)));
        assertTrue(event.getEventType().equals("type"));
        assertTrue(event.getDeviceId().equals("deviceId"));
        assertTrue(event.getObjectId() == null);
        assertThat(event.getTimestamp(), is(equalTo(now.withZone(DateTimeZone.UTC))));
        assertThat(event.getTimeseries().size(), equalTo(1));
        assertTrue(event.getTimeseries().containsKey("string"));
        assertTrue(event.getTimeseries().get("string").equals("text"));
        assertTrue(event.getObject() != null);
        assertTrue(event.getObject().getDeviceId().equals("deviceId"));
        assertThat(event.getObject().getObjectType(), is(equalTo("send event")));
        assertTrue(event.getObject().getOwnerUserName() == null);
        assertTrue(event.getObject().getOwner() == null);
        assertTrue(event.getObject().getAttributes().size() == 0);
        assertTrue(event.getObject().getEventId() == null);
        assertTrue(event.getObject().getObjectId() == null);
        assertTrue(event.getObject().getRegistrationDate() == null);
    }

    @Test
    public void timestampNull() {

        Event event = Event
                .builder()
                .withEventType("type")
                .withTimestamp(null)
                .build();

        assertTrue(event.getEventType().equals("type"));
        assertTrue(event.getTimestamp() != null);
    }

    @Test
    public void addTimeseries() {

        DateTime now = DateTime.now();

        Event event = Event
                .builder()
                .withEventType("type")
                .withAddedTimeseries("String", "text")
                .withAddedTimeseries("int", 10)
                .withAddedTimeseries("double", 10.7)
                .withAddedTimeseries("boolean", true)
                .withTimestamp(now)
                .build();

        assertTrue(event.getEventId() == null);
        assertTrue(event.getEventType().equals("type"));
        assertTrue(event.getDeviceId() == null);
        assertTrue(event.getObjectId() == null);
        assertThat(event.getTimestamp(), is(equalTo(now.withZone(DateTimeZone.UTC))));
        assertThat(event.getTimeseries().size(), equalTo(4));
        assertTrue(event.getTimeseries().containsKey("string"));
        assertThat((String)event.getTimeseries().get("string"), is(equalTo("text")));
        assertTrue(event.getTimeseries().containsKey("int"));
        assertThat((int)event.getTimeseries().get("int"), is(equalTo(10)));
        assertTrue(event.getTimeseries().containsKey("double"));
        assertThat((double)event.getTimeseries().get("double"), is(equalTo(10.7)));
        assertTrue(event.getTimeseries().containsKey("boolean"));
        assertThat((boolean)event.getTimeseries().get("boolean"), is(equalTo(true)));
        assertTrue(event.getObject() == null);
    }

    @Test
    public void timeseriesCaseSensitive() {

        DateTime now = DateTime.now();
        Map<String, Object> timeseries = new HashMap<>();
        timeseries.put("String", "text");

        Event event = Event
                .builder()
                .withEventType("type")
                .withTimeseries(timeseries)
                .withAddedTimeseries("Int", 10)
                .withTimestamp(now)
                .build();

        assertTrue(event.getEventType().equals("type"));
        assertThat(event.getTimestamp(), is(equalTo(now.withZone(DateTimeZone.UTC))));
        assertThat(event.getTimeseries().size(), equalTo(2));
        assertTrue(event.getTimeseries().containsKey("string"));
        assertTrue(event.getTimeseries().get("string").equals("text"));
        assertTrue(event.getTimeseries().containsKey("int"));
        assertThat((int)event.getTimeseries().get("int"), is(equalTo(10)));
    }

    @Test
    public void eventTypeBlank() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("X_Event_Type cannot be null or empty");
        Event.builder().withEventType("").build();
    }

    @Test
    public void eventTypeNull() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("X_Event_Type cannot be null or empty");
        Event.builder().withEventType(null).build();
    }

    @Test
    public void timeseriesNull() {

        Event event = Event
                .builder()
                .withEventType("type")
                .withTimeseries(null)
                .build();

        assertTrue(event.getEventType().equals("type"));
        assertThat(event.getTimeseries().size(), equalTo(0));
    }
}
