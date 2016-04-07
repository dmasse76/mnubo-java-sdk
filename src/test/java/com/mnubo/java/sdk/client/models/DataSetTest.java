package com.mnubo.java.sdk.client.models;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataSetTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenCreatingDataSet_assertOk() {

        Set<Field> fields = new HashSet<Field>();

        fields.add(new Field("FT1","String","FieldT1","Field unit test 1","list"));
        fields.add(new Field("FT2","Boolean","FieldT2","Field unit test 2","none"));

        DataSet sut = new DataSet("KeyData1", "DataSet Test 1", "DataSet1", fields);

        assertThat(sut.getFields(), is(equalTo(fields)));
        assertTrue(sut.getDescription().equals("DataSet Test 1"));
        assertTrue(sut.getDisplayName().equals("DataSet1"));
        assertTrue(sut.getKey().equals("KeyData1"));
    }

    @Test
    public void whenCreatingDataSetWithNullFields_expectNullPointerException() {

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Dataset fields must not be null");
        DataSet sut = new DataSet("KeyData1", "DataSet Test 1", "DataSet1", null);
        assertTrue(sut.getFields() == null);
    }

}
