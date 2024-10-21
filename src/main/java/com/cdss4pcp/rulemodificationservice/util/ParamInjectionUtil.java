package com.cdss4pcp.rulemodificationservice.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ParamInjectionUtil {
    public static String decodeCql(String encodedCql) throws UnsupportedEncodingException {

        byte[] bytes = encodedCql.getBytes("UTF-8");
        byte[] decoded = Base64.getDecoder().decode(bytes);
        String decodedCql = new String(decoded, StandardCharsets.UTF_8);
        return decodedCql;


    }

    public static String encodeCql(String cql) throws UnsupportedEncodingException {

        byte[] bytes = cql.getBytes("UTF-8");
        byte[] encoded = Base64.getEncoder().encode(bytes);
        String encodedCql = new String(encoded, StandardCharsets.UTF_8);
        return encodedCql;


    }

}
