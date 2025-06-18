package com.cdss4pcp.rulemodificationservice.util;

import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.VersionedIdentifier;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Provides a custom implementation of LibrarySourceProvider interface for injecting parameterized CQL rules.
 * Retrieves the CQL content of a rule based on the given VersionedIdentifier.
 * If the rule is not found or the version does not match, returns null.
 */
public class ParamInjectionLibrarySourceProvider implements LibrarySourceProvider {

    private Map<String, CqlRule> encodedLibraries;

    public ParamInjectionLibrarySourceProvider(Map<String, CqlRule> encodedLibraries) {
        this.encodedLibraries = encodedLibraries;
    }

    /**
     * Retrieves the CQL content of a specific rule based on the provided VersionedIdentifier.
     *
     * @param versionedIdentifier The unique identifier for the rule to retrieve.
     * @return An InputStream containing the CQL content of the rule, or null if the rule is not found or the version does not match.
     * @throws RuntimeException if there is an issue decoding the CQL content.
     */
    @Override
    public InputStream getLibrarySource(VersionedIdentifier versionedIdentifier) {
        CqlRule rule = encodedLibraries.get(versionedIdentifier.getId());
        if (rule == null) {
            return null;
        }
        if (!rule.getVersionedIdentifier().equals(versionedIdentifier)) {
            return null;
        }
        String libraryCql = null;
        libraryCql = ParamInjectionUtil.decodeCql(rule.getEncodedCql());
        return new ByteArrayInputStream(libraryCql.getBytes());

    }


}
