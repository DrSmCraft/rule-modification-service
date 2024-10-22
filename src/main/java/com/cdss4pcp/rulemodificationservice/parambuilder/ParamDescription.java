package com.cdss4pcp.rulemodificationservice.parambuilder;


import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents a parameter description with type and value.
 */
public class ParamDescription {
    @JsonProperty("type")
    String type;
    @JsonProperty("value")
    Object value;


    @JsonProperty(value = "default", required = false)
    Object defaultValue;

    public ParamDescription(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
