package com.cdss4pcp.rulemodificationservice.parambuilder;

import com.cdss4pcp.rulemodificationservice.util.TranslationException;
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


}
