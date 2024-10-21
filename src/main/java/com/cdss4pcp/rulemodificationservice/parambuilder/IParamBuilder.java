package com.cdss4pcp.rulemodificationservice.parambuilder;

import java.util.regex.Pattern;

public interface IParamBuilder<T> {
    Pattern buildPattern(String param);
    String buildReplacement(String param, T value);
}
