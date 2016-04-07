package com.mnubo.java.sdk.client.mapper;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.joda.time.DateTimeZone.UTC;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import com.mnubo.java.sdk.client.models.Event;

public class EventDeserializerTest extends AbstractSerializerTest {

    @Test
    public void testDeserialize() throws Exception {
        DateTime now = DateTime.now();

        String json = String.format(
                "{\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"x_timestamp\":\"%s\",\"x_event_type\":\"type\",\"x_object\":{\"x_device_id\":\"deviceId\"},\"string\":\"stringValue\",\"double\":10.0,\"float\":10.0,\"list_owner\": [\"val1\",\"val2\",\"val3\"]}",
                now);

        Event event = mapper.readValue(json, Event.class);

        assertTrue(event.getEventId().toString().equals("9ab392d8-a865-48da-9035-0dc0a728b454"));
        assertTrue(event.getEventType().equals("type"));
        assertTrue(event.getDeviceId().equals("deviceId"));
        assertTrue(event.getObjectId() == null);
        assertTrue(event.getTimestamp().toString().equals(formatDate(now)));
        assertThat(event.getTimeseries().size(), equalTo(4));

        Double doubleValue = (Double) event.getTimeseries().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        Double floatValue = (Double) event.getTimeseries().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = event.getTimeseries().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));

        List listValue = (List) event.getTimeseries().get("list_owner");
        assertThat(listValue.size(), equalTo(3));
        assertThat(listValue.toString(), is(equalTo("[val1, val2, val3]")));
    }

    @Test
    public void testDeserializeWithObjectDeviceIdWrongType() throws Exception {
        DateTime now = DateTime.now();

        String json = String.format(
                "{\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"x_timestamp\":\"%s\",\"x_event_type\":\"type\",\"x_object\":{\"x_device_id\":33},\"string\":\"stringValue\",\"double\":10.0,\"float\":10.0,\"list_owner\": [\"val1\",\"val2\",\"val3\"]}",
                now);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_object.x_device_id' value '33' does not match TYPE 'TEXT'");
        mapper.readValue(json, Event.class);

        Event event = mapper.readValue(json, Event.class);

        assertTrue(event.getEventId().toString().equals("9ab392d8-a865-48da-9035-0dc0a728b454"));
        assertTrue(event.getEventType().equals("type"));
        assertTrue(event.getDeviceId() == null);
        assertTrue(event.getObjectId() == null);
        assertTrue(event.getTimestamp().toString().equals(formatDate(now)));
        assertThat(event.getTimeseries().size(), equalTo(4));

        Double doubleValue = (Double) event.getTimeseries().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        Double floatValue = (Double) event.getTimeseries().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = event.getTimeseries().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));

        List listValue = (List) event.getTimeseries().get("list_owner");
        assertThat(listValue.size(), equalTo(3));
        assertThat(listValue.toString(), is(equalTo("[val1, val2, val3]")));
    }

    @Test
    public void testDeserializeWithObjectNull() throws Exception {
        DateTime now = DateTime.now();

        String json = String.format(
                "{\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"x_timestamp\":\"%s\",\"x_event_type\":\"type\",\"x_object\":null,\"string\":\"stringValue\",\"double\":10.0,\"float\":10.0,\"list_owner\": [\"val1\",\"val2\",\"val3\"]}",
                now);

        Event event = mapper.readValue(json, Event.class);

        assertTrue(event.getEventId().toString().equals("9ab392d8-a865-48da-9035-0dc0a728b454"));
        assertTrue(event.getEventType().equals("type"));
        assertTrue(event.getDeviceId() == null);
        assertTrue(event.getObjectId() == null);
        assertTrue(event.getTimestamp().toString().equals(formatDate(now)));
        assertThat(event.getTimeseries().size(), equalTo(4));

        Double doubleValue = (Double) event.getTimeseries().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        Double floatValue = (Double) event.getTimeseries().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = event.getTimeseries().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));

        List listValue = (List) event.getTimeseries().get("list_owner");
        assertThat(listValue.size(), equalTo(3));
        assertThat(listValue.toString(), is(equalTo("[val1, val2, val3]")));
    }

    @Test
    public void testDeserializeWithObjectEmpty() throws Exception {
        DateTime now = DateTime.now();

        String json = String.format(
                "{\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"x_timestamp\":\"%s\",\"x_event_type\":\"type\",\"x_object\":{},\"string\":\"stringValue\",\"double\":10.0,\"float\":10.0,\"list_owner\": [\"val1\",\"val2\",\"val3\"]}",
                now);

        Event event = mapper.readValue(json, Event.class);

        assertTrue(event.getEventId().toString().equals("9ab392d8-a865-48da-9035-0dc0a728b454"));
        assertTrue(event.getEventType().equals("type"));
        assertTrue(event.getDeviceId() == null);
        assertTrue(event.getObjectId() == null);
        assertTrue(event.getTimestamp().toString().equals(formatDate(now)));
        assertThat(event.getTimeseries().size(), equalTo(4));

        Double doubleValue = (Double) event.getTimeseries().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        Double floatValue = (Double) event.getTimeseries().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = event.getTimeseries().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));

        List listValue = (List) event.getTimeseries().get("list_owner");
        assertThat(listValue.size(), equalTo(3));
        assertThat(listValue.toString(), is(equalTo("[val1, val2, val3]")));
    }

    @Test
    public void testDeserializeWithEventTypeNull() throws Exception {

        String json = "{\"string\":\"stringValue\",\"double\":10.0,\"float\":10.0}";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("X_Event_Type cannot be null or empty");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeCheckNull() throws Exception {
        String json = "{\"x_event_type\":\"type\"}";

        Event event = mapper.readValue(json, Event.class);

        assertThat(event.getEventType(), is(equalTo("type")));
        assertTrue(event.getDeviceId() == null);
        assertTrue(event.getObjectId() == null);
        assertTrue(event.getObject() == null);
        assertTrue(event.getEventId() == null);
        assertTrue(event.getTimeseries() != null);
        assertThat(event.getTimeseries().size(), is(equalTo(0)));
        assertTrue(event.getTimestamp() != null);
    }

    @Test
    public void testDeserializeWithSmartObject() throws Exception {
        String json = "{\"x_event_type\":\"type\",\"string\":\"stringValue\",\"double\":10.0,\"float\":10.0,\"x_object\":{\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"x_device_id\":\"deviceId\",\"x_registration_date\":\"2015-08-15T07:00:00.174Z\"}}";

        Event event = mapper.readValue(json, Event.class);

        assertThat(event.getTimeseries().size(), equalTo(3));
        assertThat(event.getDeviceId(), equalTo("deviceId"));

        Double doubleValue = (Double) event.getTimeseries().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        // Float is read as double
        Double floatValue = (Double) event.getTimeseries().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = event.getTimeseries().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));
    }

    @Test
    public void testDeserializeDatetimePatternSupported() throws Exception {
        for (Map.Entry<String, Boolean> patternSample : DATETIME_SAMPLES.entrySet()) {

            String pattern = patternSample.getKey();
            boolean status = patternSample.getValue();

            String json = format("{\"x_event_type\":\"type\",\"x_timestamp\":\"%s\"}", pattern);

            if (status) {

                Event event = mapper.readValue(json, Event.class);
                assertTrue(CompareDatetimes(event.getTimestamp(), pattern));

            } else {

                try {

                    mapper.readValue(json, Event.class);

                } catch (IllegalArgumentException ex) {

                    assertThat(ex.getMessage(), is(equalTo(format(
                            "Field 'x_timestamp' value '%s' does not match TYPE 'DATETIME' pattern supported",
                            pattern))));
                }
            }
        }
    }

    @Test
    public void testDeserializeWithTypeAndDate() throws Exception {
        DateTime now = DateTime.now();

        String json =
                format("{\"x_event_type\":\"type\",\"x_timestamp\":\"%s\",\"string\":\"stringValue\",\"double\":10.0,\"float\":10.0}",
                        formatDate(now));

        Event event = mapper.readValue(json, Event.class);

        assertThat(event.getEventType(), equalTo("type"));
        assertThat(event.getTimestamp(), equalTo(now.withZone(UTC)));

        assertThat(event.getTimeseries().size(), equalTo(3));

        Double doubleValue = (Double) event.getTimeseries().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        // Float is read as double
        Double floatValue = (Double) event.getTimeseries().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = event.getTimeseries().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));
    }

    @Test
    public void testDeserializedTimeseriesAreCaseInsensitive() throws Exception {
        DateTime now = DateTime.now();

        String json =
                format("{\"x_event_type\":\"type\",\"Val1\":\"val1\",\"VAL2\":\"val2\",\"val3\":\"val3\"}",
                        formatDate(now));

        Event event = mapper.readValue(json, Event.class);

        assertThat(event.getTimeseries().size(), equalTo(3));

        assertFalse(event.getTimeseries().containsKey("Val1"));
        assertTrue(event.getTimeseries().containsKey("val1"));
        assertFalse(event.getTimeseries().containsKey("VAL2"));
        assertTrue(event.getTimeseries().containsKey("val2"));
        assertFalse(event.getTimeseries().containsKey("Val1"));
        assertTrue(event.getTimeseries().containsKey("val3"));

        String val1 = event.getTimeseries().get("val1").toString();
        assertThat(val1, is(equalTo("val1")));

        String val2 = event.getTimeseries().get("val2").toString();
        assertThat(val2, is(equalTo("val2")));

        String val3 = event.getTimeseries().get("val3").toString();
        assertThat(val3, is(equalTo("val3")));
    }

    @Test
    public void testDeserializeWrongBooleanParsing() throws Exception {
        String json = "{\"x_event_type\":true}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_event_type' value 'true' does not match TYPE 'TEXT'");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeWrongIntParsing() throws Exception {
        String json = "{\"x_event_type\":999}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_event_type' value '999' does not match TYPE 'TEXT'");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeWrongSmartObjectType() throws Exception {
        String json = "{\"x_object\":\"object\",\"string\":\"stringValue\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_object' value 'object' does not match TYPE 'SMARTOBJECT'");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeWrongEventIdType() throws Exception {
        String json = "{\"x_event_type\":\"test\",\"event_id\":\"54545c5454-054-54\",\"string\":\"stringValue\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("UUID has to be represented by the standard 36-char representation");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeEventIdWordAttributeReserved() throws Exception
    {
        String json = "{\"event_ID\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field event_id must be lowercase.");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeObjectWordAttributeReserved() throws Exception
    {
        String json = "{\"x_oBJect\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_object must be lowercase.");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeTimeStampWordAttributeReserved() throws Exception
    {
        String json = "{\"x_timestAmp\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_timestamp must be lowercase.");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeEventTypeWordAttributeReserved() throws Exception
    {
        String json = "{\"x_evEnt_type\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_event_type must be lowercase.");
        mapper.readValue(json, Event.class);
    }

    @Test
    public void testDeserializeObjectMultiTypeAttributes() throws Exception {
        String json = "{\"x_event_type\": \"reading\",\"x_object\":{ \"x_device_id\":null,\"string\":\"text\",\"true\":true,\"false\":false,\"int\":10,\"double\":10.0},\"x_timestamp\": \"2015-11-02T16:48:12.174Z\",\"humidity\": 80.4,\"temperature\": 22.4,\"wind_speed\": 43.5 }";

        Event event = mapper.readValue(json, Event.class);

        assertThat(event.getTimeseries().size(), equalTo(3));

        assertTrue(event.getTimeseries().containsKey("temperature"));
        assertTrue(event.getTimeseries().containsKey("wind_speed"));
        assertTrue(event.getTimeseries().containsKey("humidity"));
        assertFalse(event.getTimeseries().containsKey("string"));
        assertFalse(event.getTimeseries().containsKey("true"));
        assertFalse(event.getTimeseries().containsKey("false"));
        assertFalse(event.getTimeseries().containsKey("int"));
        assertFalse(event.getTimeseries().containsKey("double"));

        assertThat(event.getTimeseries().get("temperature").toString(), is(equalTo("22.4")));
        assertThat(event.getTimeseries().get("wind_speed").toString(), is(equalTo("43.5")));
        assertThat(event.getTimeseries().get("humidity").toString(), is(equalTo("80.4")));

        assertFalse(event.getObject() == null);
    }
}
