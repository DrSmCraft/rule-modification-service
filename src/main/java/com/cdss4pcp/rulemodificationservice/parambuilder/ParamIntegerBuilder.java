package com.cdss4pcp.rulemodificationservice.parambuilder;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ParamIntegerBuilder implements IParamBuilder<Integer> {

    @Override
    public Pattern buildPattern(String param) {
        String patternString = String.format("define\\s*\\\"%s\\\":\\s*(-)?\\d+", Pattern.quote(param));
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        return pattern;
    }

    @Override
    public String buildReplacement(String param, Integer value) {
        return String.format("define \"%s\": %d", Matcher.quoteReplacement(param), value);
    }
}
