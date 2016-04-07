package com.mnubo.java.sdk.client.models;

import static com.mnubo.java.sdk.client.Constants.PRINT_OBJECT_NULL;
import static java.lang.String.format;
import static org.joda.time.DateTimeZone.UTC;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;

/**
 * Owner Bean. To build an Owner you must ask for OwnerBuilder using: the static method
 * Owner.builder()
 */
public final class Owner {

    /**
     * {@value #REGISTRATION_DATE} Constant key used during the deserialization and
     * serialization of json files.
     */
    public static final String REGISTRATION_DATE = "x_registration_date";

    /**
     * {@value #PASSWORD} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String PASSWORD = "x_password";

    /**
     * {@value #USERNAME} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String USERNAME = "username";

    /**
     * {@value #EVENT_ID} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String EVENT_ID = "event_id";

    private final String username;
    private final String password;
    private final DateTime registrationDate;
    private final Map<String, Object> attributes;
    private final UUID eventId;

    Owner(String username, String password, DateTime registrationDate, Map<String, Object> attributes, UUID eventId) {
        if(username != null) {
            this.username = username.toLowerCase();
        } else {
            this.username = null;
        }
        this.password = password;
        if(registrationDate == null) {
            this.registrationDate = null;
        } else {
            this.registrationDate = registrationDate.withZone(UTC);
        }
        this.eventId = eventId;
        this.attributes = new HashMap<String, Object>();
        if (attributes != null) {
            for(Map.Entry<String,Object> entry : attributes.entrySet()) {

                String timeSerieName = entry.getKey();
                Object timeSerieValue = entry.getValue();

                if (this.attributes.containsKey(timeSerieName.toLowerCase())) {

                    throw new IllegalArgumentException(format("Duplicate field '%s'.", timeSerieName));
                }

                this.attributes.put(timeSerieName.toLowerCase(), timeSerieValue);
            }
        }
    }

    /**
     * Builder, returns a OwnerBuilder to build immutable Owner instances.
     *
     * @return Owner Builder.
     */
    public static OwnerBuilder builder() {
        return new OwnerBuilder();
    }

    /**
     * OwnerBuilder nested class, this static Class builds immutable owner instances. this
     * needs to be requested with "Owner.builder() method".
     *
     */
    public static class OwnerBuilder {

        private String username;
        private String password;
        private DateTime registrationDate;
        private Map<String, Object> attributes = new HashMap<String, Object>();
        private UUID eventId;

        OwnerBuilder() {

        }

        /**
         * Add an user name to the Owner. Note that this parameter is mandatory.
         *
         * @param username: user name of the owner.
         * @return OwnerBuilder: current Owner builder.
         * @throws IllegalStateException a run exception will be launched if this field is
         * not present
         *
         */
        public OwnerBuilder withUsername(String username) throws IllegalStateException {
            this.username = username;
            return this;
        }

        /**
         * Add a password to the owner.
         *
         * @param password: password of the owner.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Add a registrationDate to the owner.
         *
         * @param registrationDate: registrationDate of the owner.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withRegistrationDate(DateTime registrationDate) {
            if(registrationDate == null) {
                this.registrationDate = null;
            } else {
                this.registrationDate = registrationDate.withZone(UTC);
            }
            return this;
        }

        /**
         * Add a list of attributes to the Owner.
         *
         * @param attributes: Map of attributes.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Add an attribute to the Owner.
         *
         * @param key: attribute key or name.
         * @param value: attribute value.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withAddedAttribute(String key, Object value) {
            this.attributes.put(key, value);
            return this;
        }

        /**
         * Add an event Id to the request.
         *
         * @param eventId: event Id.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withEventId(UUID eventId)
        {
            this.eventId = eventId;
            return this;
        }

        /**
         * Build the immutable Owner instance with the parameters set. Note that username
         * parameter is mandatory.
         *
         * @return Owner: immutable Owner instance built
         */
        public Owner build() {
            return new Owner(username, password, registrationDate, attributes, eventId);
        }

    }

    /**
     * returns a copy of owner's attribute. It returns an empty map if there are not
     * attributes.
     *
     * @return attributes.
     *
     */
    public Map<String, Object> getAttributes() {
        return new HashMap<String, Object>(attributes);
    }

    /**
     * returns the user name.
     *
     * @return UserName.
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns the password.
     *
     * @return password.
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     * returns the registration date.
     *
     * @return registration date.
     *
     */
    public DateTime getRegistrationDate() {
        return registrationDate;
    }

    /**
     * returns the event id associate to each request.
     *
     * @return event id.
     *
     */
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("{\n");
        toPrint.append(line2String(USERNAME, username));
        toPrint.append(line2String(PASSWORD, password));
        toPrint.append(line2String(REGISTRATION_DATE, registrationDate));
        toPrint.append(line2String(EVENT_ID, eventId != null ? eventId.toString() : ""));
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
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
