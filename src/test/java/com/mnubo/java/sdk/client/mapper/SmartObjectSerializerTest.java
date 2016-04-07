package com.mnubo.java.sdk.client.mapper;

import static java.lang.String.format;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.mnubo.java.sdk.client.models.SmartObject;

public class SmartObjectSerializerTest extends AbstractSerializerTest {
    @Test
    public void testSerialize() throws Exception {

        DateTime now = DateTime.now();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("string", "stringValue");
        attributes.put("double", 10d);
        attributes.put("float", 10f);
        attributes.put("boolean", false);

        SmartObject object = SmartObject
                .builder()
                .withEventId(UUID.fromString("98c62f5c-ad48-4ef8-8d70-dbe3a1e8b17f"))
                .withDeviceId("test")
                .withRegistrationDate(now)
                .withObjectType("type")
                .withAttributes(attributes)
                .build();

        String json = mapper.writeValueAsString(object);
        JSONAssert.assertEquals(format(
                "{\"x_registration_date\":\"%s\",\"x_device_id\":\"test\",\"x_object_type\":\"type\",\"event_id\":\"98c62f5c-ad48-4ef8-8d70-dbe3a1e8b17f\",\"boolean\":false,\"string\":\"stringValue\",\"float\":10.0,\"double\":10.0}",
                formatDate(now)), json, true);
    }

    @Test
    public void testSerializeWithOwner() throws Exception {

        SmartObject object = SmartObject.builder().withEventId(UUID.fromString("671c8315-952b-4c69-8c37-d2d58a64af9e"))
                .withDeviceId("test").withOwner("owner").build();

        String json = mapper.writeValueAsString(object);
        JSONAssert.assertEquals(
                "{\"x_device_id\":\"test\",\"x_owner\":{\"username\":\"owner\"},\"event_id\":\"671c8315-952b-4c69-8c37-d2d58a64af9e\"}",
                json, true);
    }

    @Test
    public void testSerializeWithUsernameLowerCase() throws Exception {
        SmartObject object = SmartObject
                .builder()
                .withDeviceId("test")
                .withOwner("oWner")
                .build();

        String json = mapper.writeValueAsString(object);
        JSONAssert.assertEquals(
                "{\"x_device_id\":\"test\",\"x_owner\":{\"username\":\"owner\"}}", json, true);
    }

    @Test
    public void testSerializeWithRegistrationDateNull() throws Exception {

        SmartObject object = SmartObject
                .builder()
                .withRegistrationDate(null)
                .build();

        String json = mapper.writeValueAsString(object);
        assertThat(json,equalTo("{}"));
    }

    @Test
    public void testSerializeAttributeList() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("list", Arrays.asList("1", "2"));

        SmartObject object = SmartObject
                .builder()
                .withAttributes(attributes)
                .build();

        String json = mapper.writeValueAsString(object);
        JSONAssert.assertEquals("{\"list\":[\"1\",\"2\"]}", json, true);
    }
}
