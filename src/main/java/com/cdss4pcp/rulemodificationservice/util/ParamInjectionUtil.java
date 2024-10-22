package com.cdss4pcp.rulemodificationservice.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ParamInjectionUtil {

    /**
     * Decodes the Base64 encoded string to its original form using UTF-8 encoding.
     *
     * @param encodedCql the Base64 encoded string to decode
     * @return the decoded string in its original form
     */
    public static String decodeCql(String encodedCql) {
        byte[] bytes = encodedCql.getBytes(StandardCharsets.UTF_8);
        byte[] decoded = Base64.getDecoder().decode(bytes);
        String decodedCql = new String(decoded, StandardCharsets.UTF_8);
        return decodedCql;
    }

    /**
     * Encodes the input CQL string to Base64 encoding using UTF-8 charset.
     *
     * @param cql the CQL string to encode
     * @return the Base64 encoded string
     */
    public static String encodeCql(String cql) {
        byte[] bytes = cql.getBytes(StandardCharsets.UTF_8);
        byte[] encoded = Base64.getEncoder().encode(bytes);
        String encodedCql = new String(encoded, StandardCharsets.UTF_8);
        return encodedCql;
    }

}
