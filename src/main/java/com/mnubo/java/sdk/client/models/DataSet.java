package com.mnubo.java.sdk.client.models;

import static com.mnubo.java.sdk.client.Constants.PRINT_OBJECT_NULL;

import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Hold the fields that can be searched within a dataset.
 */
public final class DataSet {

    private final String key;
    private final String description;
    private final String displayName;
    private final Set<Field> fields;

    public DataSet(String key, String description, String displayName, Set<Field> fields) {
        Preconditions.checkNotNull(key, "Dataset key must not be null");
        Preconditions.checkNotNull(fields, "Dataset fields must not be null");
        this.key = key;
        this.description = description;
        this.displayName = displayName;
        this.fields = fields;
    }

    /**
     * returns the key of the Dataset.
     *
     * @return key.
     *
     */
    public String getKey() {
        return key;
    }

    /**
     * returns the description of the Dataset.
     *
     * @return description.
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns the displayName of the Dataset.
     *
     * @return displayName.
     *
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * returns the fields of the Dataset.
     *
     * @return fields.
     *
     */
    public Set<Field> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("{\n");
        toPrint.append(line2String("key", key));
        toPrint.append(line2String("description", description));
        toPrint.append(line2String("displayName", displayName));
        for (Field f : fields) {
            toPrint.append(line2String(f.description, f.toString()));
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
