package com.mnubo.java.sdk.client.mapper;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.junit.Test;

import com.mnubo.java.sdk.client.models.SmartObject;

public class SmartObjectDeserializerTest extends AbstractSerializerTest {
    @Test
    public void testDeserialize() throws Exception {

        DateTime now = DateTime.now();

        String json = String.format(
                "{\"x_device_id\":\"test\",\"x_object_type\":\"type\",\"string\":\"stringValue\",\"list_owner\": [\"val1\",\"val2\",\"val3\"],\"double\":10.0,\"float\":10.0,\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"x_registration_date\":\"%s\",\"x_owner\":{\"username\":\"owner\"}}",
                now);

        SmartObject object = mapper.readValue(json, SmartObject.class);

        assertThat(object.getDeviceId(), equalTo("test"));
        assertTrue(object.getEventId().toString().equals("9ab392d8-a865-48da-9035-0dc0a728b454"));
        assertTrue(object.getObjectType().equals("type"));
        assertTrue(object.getOwnerUserName().equals("owner"));
        assertTrue(object.getObjectId() == null);
        assertTrue(object.getRegistrationDate().toString().equals(formatDate(now)));
        assertThat(object.getAttributes().size(), equalTo(4));

        Double doubleValue = (Double) object.getAttributes().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        // Float is read as double
        Double floatValue = (Double) object.getAttributes().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = object.getAttributes().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));

        List listValue = (List) object.getAttributes().get("list_owner");
        assertThat(listValue.size(), equalTo(3));
        assertThat(listValue.toString(), CoreMatchers.is(equalTo("[val1, val2, val3]")));
    }

    @Test
    public void testDeserializeWithOwnerUsernameIsWrongType() throws Exception {
        DateTime now = DateTime.now();

        String json = String.format(
                "{\"x_device_id\":\"test\",\"x_object_type\":\"type\",\"string\":\"stringValue\",\"list_owner\": [\"val1\",\"val2\",\"val3\"],\"double\":10.0,\"float\":10.0,\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"x_registration_date\":\"%s\",\"x_owner\":{\"username\":33}}",
                now);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_owner.username' value '33' does not match TYPE 'TEXT'");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializeWithObjectNull() throws Exception {
        DateTime now = DateTime.now();

        String json = String.format(
                "{\"x_device_id\":\"test\",\"x_object_type\":\"type\",\"string\":\"stringValue\",\"list_owner\": [\"val1\",\"val2\",\"val3\"],\"double\":10.0,\"float\":10.0,\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"x_registration_date\":\"%s\",\"x_owner\":null}",
                now);

        SmartObject object = mapper.readValue(json, SmartObject.class);

        assertThat(object.getDeviceId(), equalTo("test"));
        assertTrue(object.getEventId().toString().equals("9ab392d8-a865-48da-9035-0dc0a728b454"));
        assertTrue(object.getObjectType().equals("type"));
        assertTrue(object.getOwnerUserName() == null);
        assertTrue(object.getObjectId() == null);
        assertTrue(object.getRegistrationDate().toString().equals(formatDate(now)));
        assertThat(object.getAttributes().size(), equalTo(4));

        Double doubleValue = (Double) object.getAttributes().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        // Float is read as double
        Double floatValue = (Double) object.getAttributes().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = object.getAttributes().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));

        List listValue = (List) object.getAttributes().get("list_owner");
        assertThat(listValue.size(), equalTo(3));
        assertThat(listValue.toString(), CoreMatchers.is(equalTo("[val1, val2, val3]")));
    }

    @Test
    public void testDeserializeWithOwner() throws Exception {
        String json = "{\"x_device_id\":\"test\",\"x_owner\":{\"event_id\":\"9ab392d8-a865-48da-9035-0dc0a728b454\",\"username\":\"owner\",\"x_registration_date\":\"2015-08-28T15:08:37.577Z\",\"owner\":\"shouldnt be read\"},\"string\":\"stringValue\",\"double\":10.0,\"float\":10.0}";

        SmartObject object = mapper.readValue(json, SmartObject.class);

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("owner", "shouldnt be read");

        assertThat(object.getDeviceId(), equalTo("test"));
        assertThat(object.getOwnerUserName(), equalTo("owner"));
        assertThat(object.getAttributes().size(), equalTo(3));

        Double doubleValue = (Double) object.getAttributes().get("double");
        assertThat(doubleValue, is(equalTo(10d)));

        // Float is read as double
        Double floatValue = (Double) object.getAttributes().get("float");
        assertThat(floatValue, is(equalTo(10d)));

        String stringValue = object.getAttributes().get("string").toString();
        assertThat(stringValue, is(equalTo("stringValue")));
    }

    @Test
    public void testDeserializeWrongIntParsing() throws Exception {
        String json = "{\"x_object_type\":989}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_object_type' value '989' does not match TYPE 'TEXT'");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializeWrongBooleanParsing() throws Exception {
        String json = "{\"x_object_type\":true}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_object_type' value 'true' does not match TYPE 'TEXT'");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializeWrongOwnerType() throws Exception {
        String json = "{\"x_device_id\":\"test\",\"x_owner\":\"owner\",\"string\":\"stringValue\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_owner' value 'owner' does not match TYPE 'SMARTOBJECT'");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializeWrongEventIdType() throws Exception {
        String json = "{\"x_device_id\":\"test\",\"event_id\":\"54545c5454-054-54\",\"string\":\"stringValue\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("UUID has to be represented by the standard 36-char representation");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializedTimeseriesAreCaseInsensitive() throws Exception {
        String json = "{\"Val1\":\"val1\",\"VAL2\":\"val2\",\"val3\":\"val3\"}";

        SmartObject object = mapper.readValue(json, SmartObject.class);

        assertThat(object.getAttributes().size(), equalTo(3));

        assertFalse(object.getAttributes().containsKey("Val1"));
        assertTrue(object.getAttributes().containsKey("val1"));
        assertFalse(object.getAttributes().containsKey("VAL2"));
        assertTrue(object.getAttributes().containsKey("val2"));
        assertFalse(object.getAttributes().containsKey("Val1"));
        assertTrue(object.getAttributes().containsKey("val3"));

        String val1 = object.getAttributes().get("val1").toString();
        assertThat(val1, is(equalTo("val1")));

        String val2 = object.getAttributes().get("val2").toString();
        assertThat(val2, is(equalTo("val2")));

        String val3 = object.getAttributes().get("val3").toString();
        assertThat(val3, is(equalTo("val3")));
    }

    @Test
    public void testDeserializeObjectMultiTypeAttributes() throws Exception {
        String json = "{\"x_device_id\": \"reading\",\"x_owner\":{ \"username\":null,\"string\":\"text\",\"true\":true,\"false\":false,\"int\":10,\"double\":10.0},\"humidity\": 80.4,\"temperature\": 22.4,\"wind_speed\": 43.5 }";

        SmartObject object = mapper.readValue(json, SmartObject.class);

        assertThat(object.getAttributes().size(), equalTo(3));

        assertTrue(object.getAttributes().containsKey("temperature"));
        assertTrue(object.getAttributes().containsKey("wind_speed"));
        assertTrue(object.getAttributes().containsKey("humidity"));
        assertFalse(object.getAttributes().containsKey("string"));
        assertFalse(object.getAttributes().containsKey("true"));
        assertFalse(object.getAttributes().containsKey("false"));
        assertFalse(object.getAttributes().containsKey("int"));
        assertFalse(object.getAttributes().containsKey("double"));

        assertThat(object.getAttributes().get("temperature").toString(), is(equalTo("22.4")));
        assertThat(object.getAttributes().get("wind_speed").toString(), is(equalTo("43.5")));
        assertThat(object.getAttributes().get("humidity").toString(), is(equalTo("80.4")));

        assertFalse(object.getOwner() == null);
        assertThat(object.getOwner().getAttributes().size(), equalTo(0));
    }

    @Test
    public void testDeserializeDatetimePatternSupported() throws Exception {
        for (Map.Entry<String, Boolean> patternSample : DATETIME_SAMPLES.entrySet()) {

            String pattern = patternSample.getKey();
            boolean status = patternSample.getValue();

            String json = format("{\"x_registration_date\":\"%s\"}", pattern);

            if (status) {

                SmartObject object = mapper.readValue(json, SmartObject.class);
                assertTrue(CompareDatetimes(object.getRegistrationDate(), pattern));

            } else {

                try {

                    mapper.readValue(json, SmartObject.class);

                } catch (IllegalArgumentException ex) {

                    assertThat(ex.getMessage(), is(equalTo(format(
                            "Field 'x_registration_date' value '%s' does not match TYPE 'DATETIME' pattern supported",
                            pattern))));
                }
            }
        }
    }

    @Test
    public void testDeserializeEventIdWordAttributeReserved() throws Exception {
        String json = "{\"event_ID\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field event_id must be lowercase.");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializeObjectTypeWordAttributeReserved() throws Exception {
        String json = "{\"x_object_typE\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_object_type must be lowercase.");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializeRegistrationDateWordAttributeReserved() throws Exception {
        String json = "{\"x_registration_Date\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_registration_date must be lowercase.");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializeDeviceIdWordAttributeReserved() throws Exception {
        String json = "{\"x_device_Id\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_device_id must be lowercase.");
        mapper.readValue(json, SmartObject.class);
    }

    @Test
    public void testDeserializeOwnerWordAttributeReserved() throws Exception {
        String json = "{\"x_owneR\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_owner must be lowercase.");
        mapper.readValue(json, SmartObject.class);
    }
}
