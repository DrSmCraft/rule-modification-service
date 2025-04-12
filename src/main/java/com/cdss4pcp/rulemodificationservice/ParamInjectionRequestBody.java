package com.cdss4pcp.rulemodificationservice;


import com.cdss4pcp.rulemodificationservice.parambuilder.ParamDescription;
import com.cdss4pcp.rulemodificationservice.util.CqlRule;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

/**
 * Represents a request body for injecting parameters, a rule, and libraries into the system.
 * Contains methods to get and set the parameters, rule, and libraries.
 */


public class ParamInjectionRequestBody {

    @JsonProperty("params")
    private HashMap<String, ParamDescription> params;
    @JsonProperty("rule")
    private CqlRule rule;
    @JsonProperty("libraries")
    private HashMap<String, CqlRule> libraries;

    @JsonProperty(value = "newLibraryName", required = false)
    private String newLibraryName;

    @JsonProperty(value = "newLibraryVersion", required = false)
    private String newLibraryVersion;

    public ParamInjectionRequestBody(HashMap<String, ParamDescription> params, CqlRule rule, HashMap<String, CqlRule> libraries) {
        this.params = params;
        this.rule = rule;
        this.libraries = libraries;
    }

    public HashMap<String, ParamDescription> getParams() {
        return params;
    }

    public void setParams(HashMap<String, ParamDescription> params) {
        this.params = params;
    }

    public CqlRule getRule() {
        return rule;
    }

    public void setRule(CqlRule rule) {
        this.rule = rule;
    }

    public HashMap<String, CqlRule> getLibraries() {
        return libraries;
    }

    public void setLibraries(HashMap<String, CqlRule> libraries) {
        this.libraries = libraries;
    }

    public String getNewLibraryName() {
        return newLibraryName;
    }

    public void setNewLibraryName(String newLibraryName) {
        this.newLibraryName = newLibraryName;
    }

    public String getNewLibraryVersion() {
        return newLibraryVersion;
    }

    public void setNewLibraryVersion(String newLibraryVersion) {
        this.newLibraryVersion = newLibraryVersion;
    }

    @Override
    public String toString() {
        return "ParamInjectionRequestBody{" +
                "params=" + params +
                ", rule=" + rule +
                ", libraries=" + libraries +
                '}';
    }
}
