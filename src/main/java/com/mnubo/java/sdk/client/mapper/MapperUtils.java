package com.mnubo.java.sdk.client.mapper;

import static java.lang.String.format;
import static org.joda.time.DateTimeZone.UTC;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;

abstract class MapperUtils {

    static final String TEXT_TYPE = "TEXT";
    static final String UUID_TYPE = "UUID";
    static final String DATETIME_TYPE = "DATETIME";
    static final String OWNER_INSTANCE_TYPE = "OWNER";
    static final String OBJECT_INSTANCE_TYPE = "SMARTOBJECT";

    static final List<String> DATETIME_PATTERNS = Arrays.asList(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    static final DateTimeParser[] dateTimeParsers;

    static {

        dateTimeParsers = new DateTimeParser[DATETIME_PATTERNS.size()];

        for(int point = 0; point < DATETIME_PATTERNS.size(); point++) {

            dateTimeParsers[point] = DateTimeFormat.forPattern(DATETIME_PATTERNS.get(point)).getParser();
        }
    }

    static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().append(null,
            dateTimeParsers).toFormatter();

    static ObjectMapper objectMapper = ObjectMapperConfig.getObjectMapper();

    static <T> T readValue(String content, Class<T> valueType) throws IllegalStateException {
        try {
            return objectMapper.readValue(content, valueType);
        }
        catch (Exception e) {
            throw new IllegalStateException("reading json content", e.getCause());
        }
    }

    static <V, T> Map<V, T> readValuesAsMap(JsonParser parser, Class<V> keyType, Class<T> valueType)
            throws IllegalStateException {
        try {
            MapType stringMapTypew = MapType.construct(HashMap.class, SimpleType.construct(keyType),
                    SimpleType.construct(valueType));
            return (HashMap<V, T>) objectMapper.readValue(parser, stringMapTypew);
        }
        catch (Exception e) {
            throw new IllegalStateException("reading json content", e.getCause());
        }

    }

    static void throwIfNotProperType(String name, Object value, String type) {
        if(value != null ) {
            switch (type) {
                case TEXT_TYPE:
                    if (!(value instanceof String)) {
                        throwIllegalArgument(name, value.toString(), type);
                    }
                    break;
                case DATETIME_TYPE:
                    try {
                        dateTimeFormatter.parseDateTime(value.toString());
                    } catch (Exception ex) {
                        throw new IllegalArgumentException(
                                format("Field '%s' value '%s' does not match TYPE '%s' pattern supported", name, value,
                                        type));
                    }
                    break;
                case UUID_TYPE:
                    try {
                        UUID.fromString(value.toString());
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("UUID has to be represented by the standard 36-char representation");
                    }
                    break;
                case OWNER_INSTANCE_TYPE:
                    if (!(value instanceof Map)) {
                        throwIllegalArgument(name, value.toString(), type);
                    }
                    break;
                case OBJECT_INSTANCE_TYPE:
                    if (!(value instanceof Map)) {
                        throwIllegalArgument(name, value.toString(), type);
                    }
                    break;
            }
        }
    }

    static void throwIllegalArgument(String name, String value, String type) {
        throw new IllegalArgumentException(format("Field '%s' value '%s' does not match TYPE '%s'", name, value, type));
    }

    static String convertToString(Object value) {

        if(value == null) {

            return null;
        } else {

            return value.toString();
        }
    }

    static UUID convertToUUID(Object value) {

        if(value == null) {

            return null;
        } else {

            return UUID.fromString(value.toString());
        }
    }

    static DateTime convertToDatetime(Object value) {

        if(value == null) {

            return null;
        } else {

            return DateTime.parse(value.toString()).withZone(UTC);
        }
    }
}
