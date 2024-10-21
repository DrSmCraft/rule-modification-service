package com.cdss4pcp.rulemodificationservice.util;


import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CqlRuleTests {


    @Test
    public void test_create_cqlrule_with_valid_values() {
        CqlRule cqlRule = new CqlRule("123", "1.0", "encodedCqlContent");
        assertEquals("123", cqlRule.getId());
        assertEquals("1.0", cqlRule.getVersion());
        assertEquals("encodedCqlContent", cqlRule.getEncodedCql());
    }

    @Test
    public void test_retrieve_properties_from_cqlrule() {
        CqlRule cqlRule = new CqlRule("456", "2.0", "anotherEncodedCql");
        assertEquals("456", cqlRule.getId());
        assertEquals("2.0", cqlRule.getVersion());
        assertEquals("anotherEncodedCql", cqlRule.getEncodedCql());
    }

    @Test
    public void test_create_cqlrule_with_null_values() {
        CqlRule cqlRule = new CqlRule(null, null, null);
        assertNull(cqlRule.getId());
        assertNull(cqlRule.getVersion());
        assertNull(cqlRule.getEncodedCql());
    }

    @Test
    public void test_id_or_version_special_characters() {
        CqlRule cqlRule = new CqlRule("id@123", "version#456", "encodedCql");

        assertEquals("id@123", cqlRule.getId());
        assertEquals("version#456", cqlRule.getVersion());
    }

    @Test
    public void test_versioned_identifier_compatibility() {
        CqlRule cqlRule = new CqlRule("rule_id_123", "v1", "encoded_cql_content");
        VersionedIdentifier versionedIdentifier = cqlRule.getVersionedIdentifier();

        VersionedIdentifier testIdentifier = new VersionedIdentifier();
        testIdentifier.setId("rule_id_123");
        testIdentifier.setVersion("v1");
        assertEquals("rule_id_123", versionedIdentifier.getId());
        assertEquals("v1", versionedIdentifier.getVersion());
        assertEquals(versionedIdentifier, testIdentifier);
    }
}
