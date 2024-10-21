package com.cdss4pcp.rulemodificationservice.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hl7.elm.r1.VersionedIdentifier;


public class CqlRule {
    @JsonProperty("id")
    String id;
    @JsonProperty("version")
    String version;
    @JsonProperty("content")
    String encodedCql;

    public CqlRule(String id, String version, String encodedCql) {
        this.id = id;
        this.version = version;
        this.encodedCql = encodedCql;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getEncodedCql() {
        return encodedCql;
    }

    @JsonIgnore
    public VersionedIdentifier getVersionedIdentifier() {
        VersionedIdentifier identifier = new VersionedIdentifier();
        identifier.setId(id);
        identifier.setVersion(version);
        return identifier;
    }
}
