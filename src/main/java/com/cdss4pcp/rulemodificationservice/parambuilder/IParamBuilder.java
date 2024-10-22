package com.cdss4pcp.rulemodificationservice.parambuilder;

import java.util.regex.Pattern;

/**
 * Interface for building patterns and replacements based on a given parameter.
 *
 * @param <T> the type of value used for replacement
 */
public interface IParamBuilder<T> {
    Pattern buildPattern(String param);
    String buildReplacement(String param, T value);
}
