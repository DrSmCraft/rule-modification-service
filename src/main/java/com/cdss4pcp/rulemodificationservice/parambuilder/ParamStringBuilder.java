package com.cdss4pcp.rulemodificationservice.parambuilder;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ParamStringBuilder implements IParamBuilder<String> {
    @Override
    public Pattern buildPattern(String param) {
        String patternString = String.format("define\\s*\\\"%s\\\":\\s*'[a-zA-Z0-9]*'", Pattern.quote(param));
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        return pattern;
    }

    @Override
    public String buildReplacement(String param, String value) {
        return String.format("define \"%s\": '%s'", Matcher.quoteReplacement(param), Matcher.quoteReplacement(value));
    }
}
