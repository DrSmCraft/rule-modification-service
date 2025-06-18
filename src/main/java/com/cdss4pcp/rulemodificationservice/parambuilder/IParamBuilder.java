package com.cdss4pcp.rulemodificationservice.parambuilder;

import java.util.regex.Pattern;

/**
 * Interface for building patterns and replacements based on a given parameter.
 *
 * @param <T> the type of value used for replacement
 */
public interface IParamBuilder<T> {

    /**
     * Builds and returns a regex Pattern object based on the input parameter.
     *
     * @param param the parameter name to be included in the regex pattern
     * @return the compiled regex Pattern object
     */
    Pattern buildPattern(String param);

    /**
     * Builds and returns a replacement string based on the given parameter and value.
     *
     * @param param the parameter name to be replaced
     * @param value the value to use for replacement
     * @return the replacement string
     */
    String buildReplacement(String param, T value);
}
