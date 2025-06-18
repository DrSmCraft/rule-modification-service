package com.cdss4pcp.rulemodificationservice.parambuilder;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ParamIntegerBuilder class that implements the IParamBuilder interface for String type.
 * Provides methods to build a pattern and replacement based on a given parameter.
 */
class ParamStringBuilder implements IParamBuilder<String> {

    /**
     * Builds and returns a regex Pattern object based on the input parameter.
     * The pattern matches a string that starts with 'define', followed by optional whitespace,
     * then the input parameter in quotes, a colon, optional whitespace, and a string delimited by single quotes.
     * The matching is case-insensitive.
     *
     * @param param the parameter name to be included in the regex pattern
     * @return the compiled regex Pattern object
     */
    @Override
    public Pattern buildPattern(String param) {
        String patternString = String.format("define\\s*\\\"%s\\\":\\s*'[a-zA-Z0-9]*'", Pattern.quote(param));
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        return pattern;
    }

    /**
     * Builds and returns a formatted string representing a parameter and its corresponding value.
     * The format includes the parameter enclosed in double quotes, followed by a colon and the string value.
     *
     * @param param the parameter to be included in the formatted string
     * @param value the integer value associated with the parameter
     * @return the formatted string representing the parameter and its value
     */
    @Override
    public String buildReplacement(String param, String value) {
        return String.format("define \"%s\": '%s'", Matcher.quoteReplacement(param), Matcher.quoteReplacement(value));
    }
}
