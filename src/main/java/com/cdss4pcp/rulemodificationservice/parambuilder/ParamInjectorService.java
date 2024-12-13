package com.cdss4pcp.rulemodificationservice.parambuilder;

import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class for injecting parameters into a given CQL rule based on their types.
 * Initializes param builders for Integer and String types.
 */
public class ParamInjectorService {

    HashMap<String, IParamBuilder> paramBuilders = new HashMap<>();

    public ParamInjectorService() {
        paramBuilders.put("Integer", new ParamIntegerBuilder());
        paramBuilders.put("String", new ParamStringBuilder());
        paramBuilders.put("Boolean", new ParamBooleanBuilder());
    }

    @PostConstruct
    private void postConstruct() {
        paramBuilders = new HashMap<>();
        paramBuilders.put("Integer", new ParamIntegerBuilder());
        paramBuilders.put("String", new ParamStringBuilder());
        paramBuilders.put("Boolean", new ParamBooleanBuilder());
    }

    /**
     * Injects parameters into the given CQL rule based on their types.
     *
     * @param params a HashMap containing parameter descriptions
     * @param cql    the CQL rule to inject parameters into
     * @return the modified CQL rule after parameter injection as a String
     * @throws RuntimeException if an unrecognized parameter type is encountered
     */
    public String injectParameters(HashMap<String, ParamDescription> params, String cql) {
        if (params == null) {
            return cql;
        }
        String newCQL = cql;
        for (HashMap.Entry<String, ParamDescription> entry : params.entrySet()) {
            ParamDescription value = entry.getValue();
            String param = entry.getKey();

            if (value.type.equalsIgnoreCase("Integer")) {
                Pattern pattern = paramBuilders.get("Integer").buildPattern(param);
                Integer val;
                try {
                    val = Integer.parseInt(value.value + "");
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Expected Integer but got a " + value.value.getClass() + " with value of " + value.value);
                }
                String replacement = paramBuilders.get("Integer").buildReplacement(param, val);
                Matcher matcher = pattern.matcher(newCQL);
                newCQL = matcher.replaceAll(replacement);
            } else if (value.type.equalsIgnoreCase("String")) {
                Pattern pattern = paramBuilders.get("String").buildPattern(param);
                String val;
                try {
                    val = value.value + "";
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Expected String but got a " + value.value.getClass() + " with value of " + value.value);
                }
                String replacement = paramBuilders.get("String").buildReplacement(param, val);
                Matcher matcher = pattern.matcher(newCQL);
                newCQL = matcher.replaceAll(replacement);
            } else if (value.type.equalsIgnoreCase("Boolean")) {
                Pattern pattern = paramBuilders.get("Boolean").buildPattern(param);
                Boolean val;
                try {
                    val = (Boolean) value.value;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Expected Boolean but got a " + value.value.getClass() + " with value of " + value.value);
                }
                String replacement = paramBuilders.get("Boolean").buildReplacement(param, val);
                Matcher matcher = pattern.matcher(newCQL);
                newCQL = matcher.replaceAll(replacement);
            } else {
                throw new RuntimeException("Unrecognized type: " + value.type);
            }
        }
        return newCQL;


    }
}
