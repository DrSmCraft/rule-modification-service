package com.cdss4pcp.rulemodificationservice.parambuilder;

import com.cdss4pcp.rulemodificationservice.ParamInjectorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamBooleanBuilder implements IParamBuilder<Boolean> {
    private Logger logger = LoggerFactory.getLogger(ParamInjectorController.class);

    @Override
    public Pattern buildPattern(String param) {
        String patternString = String.format("define\\s*\\\"%s\\\":\\s*((true)|(false))", Pattern.quote(param));
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        return pattern;
    }

    @Override
    public String buildReplacement(String param, Boolean value) {

        return String.format("define \"%s\": %s", Matcher.quoteReplacement(param), Matcher.quoteReplacement("false"));
    }
}
