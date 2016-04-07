package com.mnubo.java.sdk.client.mapper;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import com.mnubo.java.sdk.client.models.Owner;

public class OwnerDeserializerTest extends AbstractSerializerTest {
    @Test
    public void testDeserialize() throws Exception {

        DateTime now = DateTime.now();

        String json = String.format(
                "{\"username\":\"test\",\"x_password\":\"password\",\"x_registration_date\":\"%s\",\"age\": 89,\"list_owner\": [\"val1\",\"val2\",\"val3\"],\"event_id\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}",
                now);

        Owner owner = mapper.readValue(json, Owner.class);

        assertThat(owner.getUsername(), equalTo("test"));
        assertTrue(owner.getEventId().toString().equals("46aabccd-4442-6665-a1f0-49889330eaf3"));
        assertTrue(owner.getPassword().equals("password"));
        assertTrue(owner.getRegistrationDate().toString().equals(formatDate(now)));
        assertThat(owner.getAttributes().size(), equalTo(2));

        int intValue = (int)owner.getAttributes().get("age");
        assertThat(intValue, is(equalTo(89)));

        List listValue = (List) owner.getAttributes().get("list_owner");
        assertThat(listValue.size(), equalTo(3));
        assertThat(listValue.toString(), is(equalTo("[val1, val2, val3]")));
    }

    @Test
    public void testDeserializeCheckNull() throws Exception {
        String json = "{}";

        Owner owner = mapper.readValue(json, Owner.class);

        assertTrue(owner.getPassword() == null);
        assertTrue(owner.getUsername() == null);
        assertTrue(owner.getRegistrationDate() == null);
        assertTrue(owner.getEventId() == null);
        assertTrue(owner.getAttributes() != null);
        assertThat(owner.getAttributes().size(), is(equalTo(0)));
    }

    @Test
    public void testDeserializeWrongNumberparsing() throws Exception
    {
        String json = "{\"x_password\":9898.3}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'x_password' value '9898.3' does not match TYPE 'TEXT'");
        mapper.readValue(json, Owner.class);
    }

    @Test
    public void testDeserializeWrongEventIdType() throws Exception
    {
        String json = "{\"username\":\"test\",\"event_id\":\"54545c5454-054-54\",\"string\":\"stringValue\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("UUID has to be represented by the standard 36-char representation");
        mapper.readValue(json, Owner.class);
    }

    @Test
    public void testDeserializedTimeseriesAreCaseInsensitive() throws Exception
    {
        String json = "{\"Val1\":\"val1\",\"VAL2\":\"val2\",\"val3\":\"val3\"}";

        Owner owner = mapper.readValue( json , Owner.class );

        assertThat( owner.getAttributes().size() , equalTo( 3 ) );

        assertFalse(owner.getAttributes().containsKey("Val1"));
        assertTrue(owner.getAttributes().containsKey("val1"));
        assertFalse(owner.getAttributes().containsKey("VAL2"));
        assertTrue(owner.getAttributes().containsKey("val2"));
        assertFalse(owner.getAttributes().containsKey("Val1"));
        assertTrue(owner.getAttributes().containsKey("val3"));

        String val1 = owner.getAttributes().get( "val1" ).toString();
        assertThat( val1 , is( equalTo( "val1" ) ) );

        String val2 = owner.getAttributes().get( "val2" ).toString();
        assertThat( val2 , is( equalTo( "val2" ) ) );

        String val3 = owner.getAttributes().get( "val3" ).toString();
        assertThat( val3 , is( equalTo( "val3" ) ) );
    }

    @Test
    public void testDeserializeWrongBooleanParsing() throws Exception
    {
        String json = "{\"username\":false}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Field 'username' value 'false' does not match TYPE 'TEXT'");
        mapper.readValue(json, Owner.class);
    }

    @Test
    public void testDeserializeWithListAsAttribute() throws Exception
    {
        String json = "{\"age\": 89,\"list_owner\": [\"val1\",\"val2\",\"val3\"]}";

        Owner owner = mapper.readValue( json , Owner.class );

        assertThat( owner.getAttributes().size() , equalTo( 2 ) );

        assertTrue(owner.getAttributes().containsKey("list_owner"));

        String val1 = owner.getAttributes().get( "list_owner" ).toString();
        assertThat( val1 , is( equalTo( "[val1, val2, val3]" ) ) );
    }

    @Test
    public void testDeserializeDatetimePatternSupported() throws Exception {
        for(Map.Entry<String,Boolean> patternSample : DATETIME_SAMPLES.entrySet()) {

            String pattern = patternSample.getKey();
            boolean status = patternSample.getValue();

            String json = format("{\"x_registration_date\":\"%s\"}", pattern);

            if(status) {

                Owner owner = mapper.readValue(json, Owner.class);
                assertTrue(CompareDatetimes(owner.getRegistrationDate(), pattern));

            } else {

                try {

                    mapper.readValue(json, Owner.class);

                } catch (IllegalArgumentException ex) {

                    assertThat(ex.getMessage(), is(equalTo(format(
                            "Field 'x_registration_date' value '%s' does not match TYPE 'DATETIME' pattern supported",
                            pattern))));
                }
            }
        }
    }

    @Test
    public void testDeserializeEventIdWordAttributeReserved() throws Exception
    {
        String json = "{\"event_ID\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field event_id must be lowercase.");
        mapper.readValue(json, Owner.class);
    }

    @Test
    public void testDeserializePasswordWordAttributeReserved() throws Exception
    {
        String json = "{\"x_paSSword\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_password must be lowercase.");
        mapper.readValue(json, Owner.class);
    }

    @Test
    public void testDeserializeRegistrationDateWordAttributeReserved() throws Exception
    {
        String json = "{\"x_registration_Date\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field x_registration_date must be lowercase.");
        mapper.readValue(json, Owner.class);
    }

    @Test
    public void testDeserializeUsernameWordAttributeReserved() throws Exception
    {
        String json = "{\"userName\":\"46aabccd-4442-6665-a1f0-49889330eaf3\"}";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Reserved field username must be lowercase.");
        mapper.readValue(json, Owner.class);
    }
}
