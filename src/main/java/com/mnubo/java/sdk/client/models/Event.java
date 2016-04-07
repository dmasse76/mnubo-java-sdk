package com.mnubo.java.sdk.client.models;

import static com.mnubo.java.sdk.client.Constants.PRINT_OBJECT_NULL;
import static com.mnubo.java.sdk.client.models.SmartObject.DEVICE_ID;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.notBlank;
import static java.lang.String.format;
import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;

/**
 * Event Bean. To build an event you must ask for EventBuilder using: the static method
 * event.builder()
 */
public final class Event {

    /**
     * {@value #EVENT_ID} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String EVENT_ID = "event_id";

    /**
     * {@value #OBJECT} Constant key used during the deserialization and serialization of
     * json files.
     */
    public static final String OBJECT = "x_object";

    /**
     * {@value #EVENT_TYPE} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String EVENT_TYPE = "x_event_type";

    /**
     * {@value #TIMESTAMP} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String TIMESTAMP = "x_timestamp";

    private final UUID eventId;
    private final SmartObject smartObject;
    private final String eventType;
    private final DateTime timestamp;
    private final Map<String, Object> timeseries;

    Event(UUID eventId, SmartObject smartObject, String eventType, DateTime timestamp, Map<String, Object> timeseries) {
        notBlank(eventType, "X_Event_Type cannot be null or empty");
        this.eventId = eventId;
        this.smartObject = smartObject;
        this.eventType = eventType;
        if (timestamp != null) {
            this.timestamp = timestamp.withZone(UTC);
        }
        else {
            this.timestamp = now().withZone(UTC);
        }
        this.timeseries = new HashMap<String, Object>();
        if (timeseries != null) {
            for(Map.Entry<String,Object> entry : timeseries.entrySet()) {

                String timeSerieName = entry.getKey();
                Object timeSerieValue = entry.getValue();

                if (this.timeseries.containsKey(timeSerieName.toLowerCase())) {

                    throw new IllegalArgumentException(format("Duplicate field '%s'.", timeSerieName));
                }

                this.timeseries.put(timeSerieName.toLowerCase(), timeSerieValue);
            }
        }
    }

    /**
     * Builder, returns a EventBuilder to build immutable event instances.
     *
     * @return EventBuilder Builder.
     */
    public static EventBuilder builder() {
        return new EventBuilder();
    }

    /**
     * EventBuilder nested class, this static Class builds immutable events. this needs to
     * be requested with "Event.builder() method".
     *
     */
    public static class EventBuilder {

        private UUID eventId;
        private SmartObject smartObject;
        private String eventType;
        private DateTime timestamp = now().withZone(UTC);
        private Map<String, Object> timeseries = new HashMap<String, Object>();

        EventBuilder() {

        }

        /**
         * Add an eventId to the event. This field is optional and will be set by mnubo in
         * case it is not present.
         *
         * @param eventId: uuid event id
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withEventID(UUID eventId) {
            this.eventId = eventId;
            return this;
        }

        /**
         * Add a device Id to the event to be posted. this is the object (device_Id) where
         * this event will be sent.
         *
         * @param deviceId: object device id
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withSmartObject(String deviceId) {
            this.smartObject = SmartObject.builder().withDeviceId(deviceId).withObjectType("send event").build();
            return this;
        }

        /**
         * Add an eventType to the event. This parameter is mandatory.
         *
         * @param eventType: event type
         * @return EventBuilder: current event builder.
         * @throws IllegalStateException a run exception will be launched if this field is
         * not present
         *
         */
        public EventBuilder withEventType(String eventType) throws IllegalStateException {
            this.eventType = eventType;
            return this;
        }

        /**
         * Add a timestamp, date to the event. if this parameter is not set the current
         * time is set by default.
         *
         * @param timestamp: DateTime of the post.
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withTimestamp(DateTime timestamp) {
            if(timestamp == null) {
                this.timestamp = null;
            } else {
                this.timestamp = timestamp.withZone(UTC);
            }
            return this;
        }

        /**
         * Add a list of timeseries to the event.
         *
         * @param timeseries: Map of timeseries.
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withTimeseries(Map<String, Object> timeseries) {
            this.timeseries = timeseries;
            return this;
        }

        /**
         * Add an timeseries to the event.
         *
         * @param key: timeseries key or name.
         * @param value: timeseries value.
         * @return SmartObjectBuilder: current Smart Object builder.
         *
         */

        public EventBuilder withAddedTimeseries(String key, Object value) {
            this.timeseries.put(key, value);
            return this;
        }

        /**
         * Build the immutable event with parameters set. Note that Event_type parameter
         * is mandatory.
         * @return Event: immutable event instance built.
         */
        public Event build() {
            return new Event(eventId, smartObject, eventType, timestamp, timeseries);
        }

    }

    /**
     * returns a copy of event's timeseries. it returns an empty map if there are not
     * timeseries.
     *
     * @return timeseries.
     *
     */
    public Map<String, Object> getTimeseries() {
        return new HashMap<String, Object>(timeseries);
    }

    /**
     * returns Device Id of the object to post the event. It could be null.
     *
     * @return attributes.
     *
     */
    public String getDeviceId() {
        if (smartObject == null || smartObject.getDeviceId() == null) {
            return null;
        }
        return smartObject.getDeviceId();
    }

    /**
     * returns Object Id of the object to post the event. It could be null.
     *
     * @return attributes.
     *
     */
    public String getObjectId() {
        if (smartObject == null || smartObject.getObjectId() == null) {
            return null;
        }
        return smartObject.getObjectId().toString();
    }

    /**
     * returns eventId of the event. this could be null if it was not defined.
     *
     * @return attributes.
     *
     */
    public UUID getEventId() {
        return eventId;
    }

    /**
     * returns smartObjectId of the event. It could be null if the object was not defined.
     *
     * @return attributes.
     *
     */
    public SmartObject getObject() {
        return smartObject;
    }

    /**
     * returns event type of the event.
     *
     * @return attributes.
     *
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * returns timestamp of the event.
     *
     * @return timestamp.
     *
     */
    public DateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("{\n");
        toPrint.append(line2String(OBJECT + "." + DEVICE_ID, smartObject != null ? smartObject.getDeviceId() : null));
        toPrint.append(line2String(EVENT_ID, eventId));
        toPrint.append(line2String(EVENT_TYPE, eventType));
        toPrint.append(line2String(TIMESTAMP, timestamp));
        for (Map.Entry<String, Object> entry : timeseries.entrySet()) {
            toPrint.append(line2String(entry.getKey(), entry.getValue()));
        }
        toPrint.append("}\n");
        return toPrint.toString();

    }

    private String line2String(String name, Object value) {
        StringBuilder build = new StringBuilder();
        if (name != null) {
            build.append("     " + name + " : ");
            if (value != null) {
                build.append(value);
            }
            else {
                build.append(PRINT_OBJECT_NULL);
            }
            build.append("\n");
        }
        return build.toString();
    }

}
