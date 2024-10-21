package com.cdss4pcp.rulemodificationservice.util;

import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.VersionedIdentifier;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ParamInjectionLibrarySourceProvider implements LibrarySourceProvider {

    private Map<String, CqlRule> encodedLibraries;

    public ParamInjectionLibrarySourceProvider(Map<String, CqlRule> encodedLibraries) {
        this.encodedLibraries = encodedLibraries;
    }

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
        try {
            libraryCql = ParamInjectionUtil.decodeCql(rule.getEncodedCql());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(libraryCql.getBytes());

    }


}
