package com.cdss4pcp.rulemodificationservice.parambuilder;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class for injecting parameters into a given CQL rule based on their types.
 * Initializes param builders for Integer and String types.
 *
 */
public class ParamInjectorService {

    HashMap<String, IParamBuilder> paramBuilders = new HashMap<>();

    public ParamInjectorService() {
        paramBuilders.put("Integer", new ParamIntegerBuilder());
        paramBuilders.put("String", new ParamStringBuilder());
    }

    @PostConstruct
    private void postConstruct() {
        paramBuilders = new HashMap<>();
        paramBuilders.put("Integer", new ParamIntegerBuilder());
        paramBuilders.put("String", new ParamStringBuilder());
    }

    /**
     * Injects parameters into the given CQL rule based on their types.
     *
     * @param params a HashMap containing parameter descriptions
     * @param cql the CQL rule to inject parameters into
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
                String replacement = paramBuilders.get("Integer").buildReplacement(param, Integer.parseInt(value.value + ""));
                Matcher matcher = pattern.matcher(newCQL);
                newCQL = matcher.replaceAll(replacement);
            } else if (value.type.equalsIgnoreCase("String")) {
                Pattern pattern = paramBuilders.get("String").buildPattern(param);
                String replacement = paramBuilders.get("String").buildReplacement(param, value.value);
                Matcher matcher = pattern.matcher(newCQL);
                newCQL = matcher.replaceAll(replacement);
            } else {
                throw new RuntimeException("Unrecognized type: " + value.type);
            }
        }
        return newCQL;


    }
}
