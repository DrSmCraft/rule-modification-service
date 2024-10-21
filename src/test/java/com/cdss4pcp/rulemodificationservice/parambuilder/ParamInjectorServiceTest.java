package com.cdss4pcp.rulemodificationservice.parambuilder;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
