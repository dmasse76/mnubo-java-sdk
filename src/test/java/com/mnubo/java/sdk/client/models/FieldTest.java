package com.mnubo.java.sdk.client.models;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FieldTest {

    @Test
    public void whenCreatingField_assertValueOK() {

        Field sut = new Field("FBT","Double","FieldBT","Field builder test","list");

        assertTrue(sut.getContainerType().equals("list" ));
        assertTrue(sut.getDescription().equals("Field builder test"));
        assertTrue(sut.getDisplayName().equals("FieldBT"));
        assertTrue(sut.getHighLevelType().equals("Double"));
        assertTrue(sut.getKey().equals("FBT"));
    }

    @Test
    public void ContainerTypeNull() {

        Field sut = new Field("FCT","Long","FieldCT","Field container test","none");

        assertTrue(sut.getContainerType() == "none");
        assertTrue(sut.getDescription().equals("Field container test"));
        assertTrue(sut.getDisplayName().equals("FieldCT"));
        assertTrue(sut.getHighLevelType().equals("Long"));
        assertTrue(sut.getKey().equals("FCT"));
    }
}
