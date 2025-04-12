package com.cdss4pcp.rulemodificationservice.parambuilder;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParamInjectorServiceTest {

    @Test
    public void test_replace_integer_parameters() {
        ParamInjectorService service = new ParamInjectorService();
        HashMap<String, ParamDescription> params = new HashMap<>();
        params.put("age", new ParamDescription("Integer", 30));
        String cql = "define \"age\": 15";
        String expectedCQL = "define \"age\": 30";
        String result = service.injectParameters(params, cql);
        assertEquals(expectedCQL, result);
    }

    @Test
    public void test_replace_invalid_integer_parameters() {
        ParamInjectorService service = new ParamInjectorService();
        HashMap<String, ParamDescription> params = new HashMap<>();
        String cql = "define \"age\": 15";
        params.put("age", new ParamDescription("Integer", "30jsk"));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> service.injectParameters(params, cql), "Expected Integer but got a class java.lang.String with value of 30jsk");
        assertEquals("Expected Integer but got a class java.lang.String with value of 30jsk", thrown.getMessage());
    }

    @Test
    public void test_replace_library_name() {
        ParamInjectorService service = new ParamInjectorService();
        String cql = "library 'test1'";
        String expectedCQL = "library 'test2'";
        String result = service.injectNewLibraryNameAndVersion("test2", null, cql);
        assertEquals(expectedCQL, result);

    }

    @Test
    public void test_replace_library_name_no_quotes() {
        ParamInjectorService service = new ParamInjectorService();
        String cql = "library test1";
        String expectedCQL = "library 'test2'";
        String result = service.injectNewLibraryNameAndVersion("test2", null, cql);
        assertEquals(expectedCQL, result);

    }

    @Test
    public void test_replace_library_name_too_many_quotes() {
        ParamInjectorService service = new ParamInjectorService();
        String cql = "library test1";
        String expectedCQL = "library 'test2'";
        String result = service.injectNewLibraryNameAndVersion("'test2'", null, cql);
        assertEquals(expectedCQL, result);

    }

    @Test
    public void test_replace_library_name_and_version() {
        ParamInjectorService service = new ParamInjectorService();
        String cql = "library 'test1' version '1.0.0'";
        String expectedCQL = "library 'test2' version '2.0.0'";
        String result = service.injectNewLibraryNameAndVersion("test2", "2.0.0", cql);
        assertEquals(expectedCQL, result);

    }

    @Test
    public void test_replace_library_name_and_version_too_many_quotes() {
        ParamInjectorService service = new ParamInjectorService();
        String cql = "library 'test1' version '1.0.0'";
        String expectedCQL = "library 'test2' version '2.0.0'";
        String result = service.injectNewLibraryNameAndVersion("'test2'", "'2.0.0'", cql);
        assertEquals(expectedCQL, result);

    }

    @Test
    public void test_replace_library_name_and_version_no_quote() {
        ParamInjectorService service = new ParamInjectorService();
        String cql = "library 'test1' version 1.0.0";
        String expectedCQL = "library 'test2' version '2.0.0'";
        String result = service.injectNewLibraryNameAndVersion("test2", "2.0.0", cql);
        assertEquals(expectedCQL, result);

    }


    @Test
    public void test_replace_library_name_and_version_null() {
        ParamInjectorService service = new ParamInjectorService();
        String cql = "library 'test1' version '1.0.0'";
        String result = service.injectNewLibraryNameAndVersion(null, null, cql);
        assertEquals(cql, result);

    }
}
