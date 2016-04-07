package com.mnubo.java.sdk.client.models;

import static com.mnubo.java.sdk.client.Constants.PRINT_OBJECT_NULL;

import com.google.common.base.Preconditions;

/**
 * Field Bean. To build a Field you must use the FieldBuilder with the static method
 * Field.builder()
 */
public final class Field {
    
    String key;
    String highLevelType;
    String displayName;
    String description;
    String containerType;
    boolean isPrimaryKey;

    public Field(String key, String highLevelType, String displayName, String description, String containerType) {
        Preconditions.checkNotNull(key, "key must not be null");
        Preconditions.checkNotNull(highLevelType, "highLevelType must not be null");

        this.key = key;
        this.highLevelType = highLevelType;
        this.displayName = displayName;
        this.description = description;
        this.containerType = containerType != null ? containerType : "none";
    }

    /**
     * returns the key of the Field
     * 
     * @return key.
     *
     */
    public String getKey() {
        return key;
    }

    /**
     * returns the high Level Type of the Field
     * 
     * @return highLevelType.
     *
     */
    public String getHighLevelType() {
        return highLevelType;
    }

    /**
     * returns the display name of the Field
     * 
     * @return displayName.
     *
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * returns the description of the Field
     * 
     * @return description.
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns the container type of the Field
     * 
     * @return containerType.
     *
     */
    public String getContainerType() {
        return containerType;
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("{\n");
        toPrint.append(line2String("key", key));
        toPrint.append(line2String("highLevelType", highLevelType));
        toPrint.append(line2String("displayName", displayName));
        toPrint.append(line2String("containerType", containerType.toString()));
        toPrint.append(line2String("isPrimaryKey", isPrimaryKey));
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
