package com.mnubo.java.sdk.client.mapper;

import static org.joda.time.DateTimeZone.UTC;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractSerializerTest {
    protected Map<String, Boolean> DATETIME_SAMPLES = new HashMap<String, Boolean>() {{
        put("2015-08-15T07:00:00.174Z", true);
        put("2009-07-16T09:20:30-05:00", true);
        put("2009-07-16", false);
        put("20090716", false);
        put("30.06.09", false);
        put("text", false);
        put("Tue, Jun 30, '09", false);
        put("2009.June.30 08:29 AM", false);
        put("2012-01-31T23:59:59.999", true);
        put("2012-01-31T23:59:59.979+02:00", true);
    }};
    protected ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();

    protected String formatDate(DateTime date) {
        return date.withZone(DateTimeZone.UTC).toString();
    }

    protected boolean CompareDatetimes(DateTime formatUTC, String anyFormat) {
        return formatUTC.toString().equals(DateTime.parse(anyFormat).withZone(UTC).toString());
    }

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();
}
