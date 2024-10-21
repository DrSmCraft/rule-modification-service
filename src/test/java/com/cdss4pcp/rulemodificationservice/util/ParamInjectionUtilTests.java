package com.cdss4pcp.rulemodificationservice.util;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParamInjectionUtilTests {

    @Test
    public void test_valid_encoding() throws UnsupportedEncodingException {
        String testString = "Hello World!";
        String encoded = ParamInjectionUtil.encodeCql(testString);
        assertEquals(encoded, "SGVsbG8gV29ybGQh");
    }

    @Test
    public void test_valid_decoding() throws UnsupportedEncodingException {
        String testString = "SGVsbG8gV29ybGQh";
        String encoded = ParamInjectionUtil.decodeCql(testString);
        assertEquals(encoded, "Hello World!");
    }
}
