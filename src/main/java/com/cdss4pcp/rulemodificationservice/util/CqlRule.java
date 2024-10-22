package com.cdss4pcp.rulemodificationservice.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hl7.elm.r1.VersionedIdentifier;


/**
 * Represents a CQL rule with an ID, version, and encoded CQL content.
 * Provides methods to retrieve the ID, version, and encoded CQL content.
 * Also includes a method to get the versioned identifier of the rule.
 */
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

    /**
     * Retrieves the versioned identifier of the rule.
     *
     * @return The versioned identifier containing the ID and version of the rule.
     */
    @JsonIgnore
    public VersionedIdentifier getVersionedIdentifier() {
        VersionedIdentifier identifier = new VersionedIdentifier();
        identifier.setId(id);
        identifier.setVersion(version);
        return identifier;
    }
}
