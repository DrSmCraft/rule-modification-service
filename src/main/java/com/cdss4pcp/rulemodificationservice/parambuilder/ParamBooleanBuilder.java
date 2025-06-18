package com.cdss4pcp.rulemodificationservice.parambuilder;

import com.cdss4pcp.rulemodificationservice.ParamInjectorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamBooleanBuilder implements IParamBuilder<Boolean> {
    private Logger logger = LoggerFactory.getLogger(ParamInjectorController.class);

    /**
     * Builds and returns a regex Pattern object based on the input parameter.
     * The pattern matches a string that starts with 'define', followed by optional whitespace,
     * then the input parameter in quotes, a colon, optional whitespace, and either 'true' or 'false'.
     * The matching is case-insensitive.
     *
     * @param param the parameter name to be included in the regex pattern
     * @return the compiled regex Pattern object
     */
    @Override
    public Pattern buildPattern(String param) {
        String patternString = String.format("define\\s*\\\"%s\\\":\\s*((true)|(false))", Pattern.quote(param));
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        return pattern;
    }

    /**
     * Builds a replacement string defining the given parameter as either "true" or "false" based on the Boolean value.
     *
     * @param param the name of the parameter to define
     * @param value the Boolean value to assign to the parameter
     * @return a formatted string defining the parameter with the specified Boolean value
     */
    @Override
    public String buildReplacement(String param, Boolean value) {
        if (value != null && value) {
            return String.format("define \"%s\": %s", Matcher.quoteReplacement(param), Matcher.quoteReplacement("true"));

        } else {
            return String.format("define \"%s\": %s", Matcher.quoteReplacement(param), Matcher.quoteReplacement("false"));

        }
    }

}
