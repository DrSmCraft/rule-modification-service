package com.cdss4pcp.rulemodificationservice;

import com.cdss4pcp.rulemodificationservice.parambuilder.ParamDescription;
import com.cdss4pcp.rulemodificationservice.parambuilder.ParamInjectorService;
import com.cdss4pcp.rulemodificationservice.util.CqlRule;
import com.cdss4pcp.rulemodificationservice.util.TranslationException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ParamInjectionControllerTest {

    @InjectMocks
    ParamInjectorService injectorService;
    @InjectMocks
    ParamInjectorController controller;


    /* Test controller.inject()  */

    @Test
    public void test_valid_injection() throws UnsupportedEncodingException {
        HashMap<String, ParamDescription> params = new HashMap<>();
        params.put("$Age1", new ParamDescription("Integer", 12));
        CqlRule rule = new CqlRule("MMR1regularyoungerthan12monthsNoMMRRecommendation", "1", "bGlicmFyeSAiTU1SMXJlZ3VsYXJ5b3VuZ2VydGhhbjEybW9udGhzTm9NTVJSZWNvbW1lbmRhdGlvbiIgdmVyc2lvbiAnMScKCnVzaW5nIEZISVIgdmVyc2lvbiAnNC4wLjEnCgppbmNsdWRlICJGSElSSGVscGVycyIgdmVyc2lvbiAnNC4wLjEnCmluY2x1ZGUgIk1NUl9Db21tb25fTGlicmFyeSIgdmVyc2lvbiAnMScgY2FsbGVkIENvbW1vbgoKcGFyYW1ldGVyIEltbSBMaXN0PEltbXVuaXphdGlvbj4KCmNvbnRleHQgUGF0aWVudAoKZGVmaW5lICIkQWdlMSI6CiAgICAwCgpkZWZpbmUgIiRBZ2UxX3VuaXQiOgogICAgJ21vbnRoJwoKZGVmaW5lICIkQWdlMiI6CiAgICAxMgoKZGVmaW5lICIkQWdlMl91bml0IjoKICAgICdtb250aCcKCmRlZmluZSAiVmFjY2luZU5hbWUiOgogICAgJ01lYXNsZXMsIE11bXBzLCBhbmQgUnViZWxsYSBWaXJ1cyBWYWNjaW5lJwoKZGVmaW5lICJQYXRpZW50QWdlIjoKICAgIEFnZUluTW9udGhzKCkKCmRlZmluZSAiQ3VycmVudEFnZSI6CiAgQ29tbW9uLkdldEFnZSgiJEFnZTFfdW5pdCIpID49ICIkQWdlMSIKICAgIGFuZCBDb21tb24uR2V0QWdlKCIkQWdlMl91bml0IikgPCAiJEFnZTIiCgpkZWZpbmUgIkluUG9wdWxhdGlvbiI6CiAgIEN1cnJlbnRBZ2UgYW5kICBDb3VudChDb21tb24uRmluZFZhbGlkVmFjY2luZXMoSW1tKSkgPSAwCgpkZWZpbmUgIlJlY29tbWVuZGF0aW9uMSI6CiAgICBpZiBJblBvcHVsYXRpb24gdGhlbgogICAgICAgICdSZWNvbW1lbmRhdGlvbiAxOiBTY2hlZHVsZSAxc3QgZG9zZSBNTVIgd2hlbiBwYXRpZW50IGlzIDEyLTE1IG1vbnRocycKICAgIGVsc2UgbnVsbAoKZGVmaW5lICJSZWNvbW1lbmRhdGlvbjIiOgogICAgaWYgSW5Qb3B1bGF0aW9uIHRoZW4KICAgICAgICAnUmVjb21tZW5kYXRpb24gMjogU2NoZWR1bGUgMm5kIGRvc2UgTU1SIHdoZW4gcGF0aWVudCBpcyA0LTYgeWVhcnMnCiAgICBlbHNlIG51bGwKCgo=");
        ParamInjectionRequestBody input = new ParamInjectionRequestBody(params, rule, new HashMap<>());
        ParamInjectionRequestBody response = controller.inject(input);

        assertThat(response).isNotNull();
        assertThat(response.getParams()).isEqualTo(params);
        assertThat(response.getRule().getEncodedCql()).isEqualTo("bGlicmFyeSAiTU1SMXJlZ3VsYXJ5b3VuZ2VydGhhbjEybW9udGhzTm9NTVJSZWNvbW1lbmRhdGlvbiIgdmVyc2lvbiAnMScKCnVzaW5nIEZISVIgdmVyc2lvbiAnNC4wLjEnCgppbmNsdWRlICJGSElSSGVscGVycyIgdmVyc2lvbiAnNC4wLjEnCmluY2x1ZGUgIk1NUl9Db21tb25fTGlicmFyeSIgdmVyc2lvbiAnMScgY2FsbGVkIENvbW1vbgoKcGFyYW1ldGVyIEltbSBMaXN0PEltbXVuaXphdGlvbj4KCmNvbnRleHQgUGF0aWVudAoKZGVmaW5lICIkQWdlMSI6IDEyCgpkZWZpbmUgIiRBZ2UxX3VuaXQiOgogICAgJ21vbnRoJwoKZGVmaW5lICIkQWdlMiI6CiAgICAxMgoKZGVmaW5lICIkQWdlMl91bml0IjoKICAgICdtb250aCcKCmRlZmluZSAiVmFjY2luZU5hbWUiOgogICAgJ01lYXNsZXMsIE11bXBzLCBhbmQgUnViZWxsYSBWaXJ1cyBWYWNjaW5lJwoKZGVmaW5lICJQYXRpZW50QWdlIjoKICAgIEFnZUluTW9udGhzKCkKCmRlZmluZSAiQ3VycmVudEFnZSI6CiAgQ29tbW9uLkdldEFnZSgiJEFnZTFfdW5pdCIpID49ICIkQWdlMSIKICAgIGFuZCBDb21tb24uR2V0QWdlKCIkQWdlMl91bml0IikgPCAiJEFnZTIiCgpkZWZpbmUgIkluUG9wdWxhdGlvbiI6CiAgIEN1cnJlbnRBZ2UgYW5kICBDb3VudChDb21tb24uRmluZFZhbGlkVmFjY2luZXMoSW1tKSkgPSAwCgpkZWZpbmUgIlJlY29tbWVuZGF0aW9uMSI6CiAgICBpZiBJblBvcHVsYXRpb24gdGhlbgogICAgICAgICdSZWNvbW1lbmRhdGlvbiAxOiBTY2hlZHVsZSAxc3QgZG9zZSBNTVIgd2hlbiBwYXRpZW50IGlzIDEyLTE1IG1vbnRocycKICAgIGVsc2UgbnVsbAoKZGVmaW5lICJSZWNvbW1lbmRhdGlvbjIiOgogICAgaWYgSW5Qb3B1bGF0aW9uIHRoZW4KICAgICAgICAnUmVjb21tZW5kYXRpb24gMjogU2NoZWR1bGUgMm5kIGRvc2UgTU1SIHdoZW4gcGF0aWVudCBpcyA0LTYgeWVhcnMnCiAgICBlbHNlIG51bGwKCgo=");
    }

    @Test
    public void test_null_parameter_injection() throws UnsupportedEncodingException {
        HashMap<String, ParamDescription> params = null;
        CqlRule rule = new CqlRule("MMR1regularyoungerthan12monthsNoMMRRecommendation", "1", "bGlicmFyeSAiTU1SMXJlZ3VsYXJ5b3VuZ2VydGhhbjEybW9udGhzTm9NTVJSZWNvbW1lbmRhdGlvbiIgdmVyc2lvbiAnMScKCnVzaW5nIEZISVIgdmVyc2lvbiAnNC4wLjEnCgppbmNsdWRlICJGSElSSGVscGVycyIgdmVyc2lvbiAnNC4wLjEnCmluY2x1ZGUgIk1NUl9Db21tb25fTGlicmFyeSIgdmVyc2lvbiAnMScgY2FsbGVkIENvbW1vbgoKcGFyYW1ldGVyIEltbSBMaXN0PEltbXVuaXphdGlvbj4KCmNvbnRleHQgUGF0aWVudAoKZGVmaW5lICIkQWdlMSI6CiAgICAwCgpkZWZpbmUgIiRBZ2UxX3VuaXQiOgogICAgJ21vbnRoJwoKZGVmaW5lICIkQWdlMiI6CiAgICAxMgoKZGVmaW5lICIkQWdlMl91bml0IjoKICAgICdtb250aCcKCmRlZmluZSAiVmFjY2luZU5hbWUiOgogICAgJ01lYXNsZXMsIE11bXBzLCBhbmQgUnViZWxsYSBWaXJ1cyBWYWNjaW5lJwoKZGVmaW5lICJQYXRpZW50QWdlIjoKICAgIEFnZUluTW9udGhzKCkKCmRlZmluZSAiQ3VycmVudEFnZSI6CiAgQ29tbW9uLkdldEFnZSgiJEFnZTFfdW5pdCIpID49ICIkQWdlMSIKICAgIGFuZCBDb21tb24uR2V0QWdlKCIkQWdlMl91bml0IikgPCAiJEFnZTIiCgpkZWZpbmUgIkluUG9wdWxhdGlvbiI6CiAgIEN1cnJlbnRBZ2UgYW5kICBDb3VudChDb21tb24uRmluZFZhbGlkVmFjY2luZXMoSW1tKSkgPSAwCgpkZWZpbmUgIlJlY29tbWVuZGF0aW9uMSI6CiAgICBpZiBJblBvcHVsYXRpb24gdGhlbgogICAgICAgICdSZWNvbW1lbmRhdGlvbiAxOiBTY2hlZHVsZSAxc3QgZG9zZSBNTVIgd2hlbiBwYXRpZW50IGlzIDEyLTE1IG1vbnRocycKICAgIGVsc2UgbnVsbAoKZGVmaW5lICJSZWNvbW1lbmRhdGlvbjIiOgogICAgaWYgSW5Qb3B1bGF0aW9uIHRoZW4KICAgICAgICAnUmVjb21tZW5kYXRpb24gMjogU2NoZWR1bGUgMm5kIGRvc2UgTU1SIHdoZW4gcGF0aWVudCBpcyA0LTYgeWVhcnMnCiAgICBlbHNlIG51bGwKCgo=");
        ParamInjectionRequestBody input = new ParamInjectionRequestBody(params, rule, new HashMap<>());
        ParamInjectionRequestBody response = controller.inject(input);

        assertThat(response).isNotNull();
        assertThat(response.getParams()).isNull();
        assertThat(response.getLibraries()).isNotNull();
        assertThat(response.getRule().getEncodedCql()).isEqualTo(rule.getEncodedCql());
    }

    @Test
    public void test_null_library_injection() throws UnsupportedEncodingException {
        HashMap<String, ParamDescription> params = new HashMap<>();
        params.put("$Age1", new ParamDescription("Integer", 12));
        CqlRule rule = new CqlRule("MMR1regularyoungerthan12monthsNoMMRRecommendation", "1", "bGlicmFyeSAiTU1SMXJlZ3VsYXJ5b3VuZ2VydGhhbjEybW9udGhzTm9NTVJSZWNvbW1lbmRhdGlvbiIgdmVyc2lvbiAnMScKCnVzaW5nIEZISVIgdmVyc2lvbiAnNC4wLjEnCgppbmNsdWRlICJGSElSSGVscGVycyIgdmVyc2lvbiAnNC4wLjEnCmluY2x1ZGUgIk1NUl9Db21tb25fTGlicmFyeSIgdmVyc2lvbiAnMScgY2FsbGVkIENvbW1vbgoKcGFyYW1ldGVyIEltbSBMaXN0PEltbXVuaXphdGlvbj4KCmNvbnRleHQgUGF0aWVudAoKZGVmaW5lICIkQWdlMSI6CiAgICAwCgpkZWZpbmUgIiRBZ2UxX3VuaXQiOgogICAgJ21vbnRoJwoKZGVmaW5lICIkQWdlMiI6CiAgICAxMgoKZGVmaW5lICIkQWdlMl91bml0IjoKICAgICdtb250aCcKCmRlZmluZSAiVmFjY2luZU5hbWUiOgogICAgJ01lYXNsZXMsIE11bXBzLCBhbmQgUnViZWxsYSBWaXJ1cyBWYWNjaW5lJwoKZGVmaW5lICJQYXRpZW50QWdlIjoKICAgIEFnZUluTW9udGhzKCkKCmRlZmluZSAiQ3VycmVudEFnZSI6CiAgQ29tbW9uLkdldEFnZSgiJEFnZTFfdW5pdCIpID49ICIkQWdlMSIKICAgIGFuZCBDb21tb24uR2V0QWdlKCIkQWdlMl91bml0IikgPCAiJEFnZTIiCgpkZWZpbmUgIkluUG9wdWxhdGlvbiI6CiAgIEN1cnJlbnRBZ2UgYW5kICBDb3VudChDb21tb24uRmluZFZhbGlkVmFjY2luZXMoSW1tKSkgPSAwCgpkZWZpbmUgIlJlY29tbWVuZGF0aW9uMSI6CiAgICBpZiBJblBvcHVsYXRpb24gdGhlbgogICAgICAgICdSZWNvbW1lbmRhdGlvbiAxOiBTY2hlZHVsZSAxc3QgZG9zZSBNTVIgd2hlbiBwYXRpZW50IGlzIDEyLTE1IG1vbnRocycKICAgIGVsc2UgbnVsbAoKZGVmaW5lICJSZWNvbW1lbmRhdGlvbjIiOgogICAgaWYgSW5Qb3B1bGF0aW9uIHRoZW4KICAgICAgICAnUmVjb21tZW5kYXRpb24gMjogU2NoZWR1bGUgMm5kIGRvc2UgTU1SIHdoZW4gcGF0aWVudCBpcyA0LTYgeWVhcnMnCiAgICBlbHNlIG51bGwKCgo=");
        ParamInjectionRequestBody input = new ParamInjectionRequestBody(params, rule, null);
        ParamInjectionRequestBody response = controller.inject(input);

        assertThat(response).isNotNull();
        assertThat(response.getParams()).isEqualTo(params);
        assertThat(response.getLibraries()).isNull();
        assertThat(response.getRule().getEncodedCql()).isEqualTo("bGlicmFyeSAiTU1SMXJlZ3VsYXJ5b3VuZ2VydGhhbjEybW9udGhzTm9NTVJSZWNvbW1lbmRhdGlvbiIgdmVyc2lvbiAnMScKCnVzaW5nIEZISVIgdmVyc2lvbiAnNC4wLjEnCgppbmNsdWRlICJGSElSSGVscGVycyIgdmVyc2lvbiAnNC4wLjEnCmluY2x1ZGUgIk1NUl9Db21tb25fTGlicmFyeSIgdmVyc2lvbiAnMScgY2FsbGVkIENvbW1vbgoKcGFyYW1ldGVyIEltbSBMaXN0PEltbXVuaXphdGlvbj4KCmNvbnRleHQgUGF0aWVudAoKZGVmaW5lICIkQWdlMSI6IDEyCgpkZWZpbmUgIiRBZ2UxX3VuaXQiOgogICAgJ21vbnRoJwoKZGVmaW5lICIkQWdlMiI6CiAgICAxMgoKZGVmaW5lICIkQWdlMl91bml0IjoKICAgICdtb250aCcKCmRlZmluZSAiVmFjY2luZU5hbWUiOgogICAgJ01lYXNsZXMsIE11bXBzLCBhbmQgUnViZWxsYSBWaXJ1cyBWYWNjaW5lJwoKZGVmaW5lICJQYXRpZW50QWdlIjoKICAgIEFnZUluTW9udGhzKCkKCmRlZmluZSAiQ3VycmVudEFnZSI6CiAgQ29tbW9uLkdldEFnZSgiJEFnZTFfdW5pdCIpID49ICIkQWdlMSIKICAgIGFuZCBDb21tb24uR2V0QWdlKCIkQWdlMl91bml0IikgPCAiJEFnZTIiCgpkZWZpbmUgIkluUG9wdWxhdGlvbiI6CiAgIEN1cnJlbnRBZ2UgYW5kICBDb3VudChDb21tb24uRmluZFZhbGlkVmFjY2luZXMoSW1tKSkgPSAwCgpkZWZpbmUgIlJlY29tbWVuZGF0aW9uMSI6CiAgICBpZiBJblBvcHVsYXRpb24gdGhlbgogICAgICAgICdSZWNvbW1lbmRhdGlvbiAxOiBTY2hlZHVsZSAxc3QgZG9zZSBNTVIgd2hlbiBwYXRpZW50IGlzIDEyLTE1IG1vbnRocycKICAgIGVsc2UgbnVsbAoKZGVmaW5lICJSZWNvbW1lbmRhdGlvbjIiOgogICAgaWYgSW5Qb3B1bGF0aW9uIHRoZW4KICAgICAgICAnUmVjb21tZW5kYXRpb24gMjogU2NoZWR1bGUgMm5kIGRvc2UgTU1SIHdoZW4gcGF0aWVudCBpcyA0LTYgeWVhcnMnCiAgICBlbHNlIG51bGwKCgo=");
    }

    @Test
    public void test_null_rule_injection() {
        HashMap<String, ParamDescription> params = new HashMap<>();
        params.put("$Age1", new ParamDescription("Integer", 12));
        ParamInjectionRequestBody input = new ParamInjectionRequestBody(params, null, new HashMap<>());

        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> controller.inject(input),
                "Expected controller.inject() to throw NullPointerException,  but it didn't"
        );
        assertEquals("rule is null, please provide a valid rule", thrown.getMessage());
    }


    /* Test controller.translate()  */
    @Test
    public void test_valid_translation() throws UnsupportedEncodingException, JSONException {
        HashMap<String, ParamDescription> params = new HashMap<>();
        CqlRule rule = new CqlRule("LibraryOne", "1", "bGlicmFyeSBMaWJyYXJ5T25lCgp1c2luZyBGSElSIHZlcnNpb24gJzQuMC4xJwoKaW5jbHVkZSBGSElSSGVscGVycyB2ZXJzaW9uICc0LjAuMScKCmNvbnRleHQgUGF0aWVudAoKZGVmaW5lIHg6IFBhdGllbnQuZ2VuZGVyID0gJ2ZlbWFsZSc=");
        ParamInjectionRequestBody input = new ParamInjectionRequestBody(params, rule, new HashMap<>());
        String translated = controller.translate(input);
        String expected = "{\"library\":{\"annotation\":[{\"translatorVersion\":\"3.2.0\",\"translatorOptions\":\"EnableAnnotations,EnableLocators,DisableListDemotion,DisableListPromotion\",\"type\":\"CqlToElmInfo\"},{\"type\":\"Annotation\",\"s\":{\"r\":\"7\",\"s\":[{\"value\":[\"\",\"library LibraryOne\"]}]}}],\"identifier\":{\"id\":\"LibraryOne\"},\"schemaIdentifier\":{\"id\":\"urn:hl7-org:elm\",\"version\":\"r1\"},\"usings\":{\"def\":[{\"localIdentifier\":\"System\",\"uri\":\"urn:hl7-org:elm-types:r1\"},{\"localId\":\"1\",\"locator\":\"3:1-3:26\",\"localIdentifier\":\"FHIR\",\"uri\":\"http://hl7.org/fhir\",\"version\":\"4.0.1\",\"annotation\":[{\"type\":\"Annotation\",\"s\":{\"r\":\"1\",\"s\":[{\"value\":[\"\",\"using \"]},{\"s\":[{\"value\":[\"FHIR\"]}]},{\"value\":[\" version \",\"'4.0.1'\"]}]}}]}]},\"includes\":{\"def\":[{\"localId\":\"2\",\"locator\":\"5:1-5:35\",\"localIdentifier\":\"FHIRHelpers\",\"path\":\"FHIRHelpers\",\"version\":\"4.0.1\",\"annotation\":[{\"type\":\"Annotation\",\"s\":{\"r\":\"2\",\"s\":[{\"value\":[\"\",\"include \"]},{\"s\":[{\"value\":[\"FHIRHelpers\"]}]},{\"value\":[\" version \",\"'4.0.1'\"]}]}}]}]},\"contexts\":{\"def\":[{\"locator\":\"7:1-7:15\",\"name\":\"Patient\"}]},\"statements\":{\"def\":[{\"locator\":\"7:1-7:15\",\"name\":\"Patient\",\"context\":\"Patient\",\"expression\":{\"type\":\"SingletonFrom\",\"operand\":{\"locator\":\"7:1-7:15\",\"dataType\":\"{http://hl7.org/fhir}Patient\",\"templateId\":\"http://hl7.org/fhir/StructureDefinition/Patient\",\"type\":\"Retrieve\"}}},{\"localId\":\"7\",\"locator\":\"9:1-9:35\",\"name\":\"x\",\"context\":\"Patient\",\"accessLevel\":\"Public\",\"annotation\":[{\"type\":\"Annotation\",\"s\":{\"r\":\"7\",\"s\":[{\"value\":[\"\",\"define \",\"x\",\": \"]},{\"r\":\"6\",\"s\":[{\"r\":\"4\",\"s\":[{\"r\":\"3\",\"s\":[{\"value\":[\"Patient\"]}]},{\"value\":[\".\"]},{\"r\":\"4\",\"s\":[{\"value\":[\"gender\"]}]}]},{\"value\":[\" \",\"=\",\" \"]},{\"r\":\"5\",\"s\":[{\"value\":[\"'female'\"]}]}]}]}}],\"expression\":{\"localId\":\"6\",\"locator\":\"9:11-9:35\",\"type\":\"Equal\",\"signature\":[{\"name\":\"{urn:hl7-org:elm-types:r1}String\",\"type\":\"NamedTypeSpecifier\"},{\"name\":\"{urn:hl7-org:elm-types:r1}String\",\"type\":\"NamedTypeSpecifier\"}],\"operand\":[{\"name\":\"ToString\",\"libraryName\":\"FHIRHelpers\",\"type\":\"FunctionRef\",\"signature\":[{\"name\":\"{http://hl7.org/fhir}AdministrativeGender\",\"type\":\"NamedTypeSpecifier\"}],\"operand\":[{\"localId\":\"4\",\"locator\":\"9:11-9:24\",\"path\":\"gender\",\"type\":\"Property\",\"source\":{\"localId\":\"3\",\"locator\":\"9:11-9:17\",\"name\":\"Patient\",\"type\":\"ExpressionRef\"}}]},{\"localId\":\"5\",\"locator\":\"9:28-9:35\",\"valueType\":\"{urn:hl7-org:elm-types:r1}String\",\"value\":\"female\",\"type\":\"Literal\"}]}}]}}}";
        JSONAssert.assertEquals(expected, translated, true);
    }


    @Test
    public void test_invalid_translation() throws UnsupportedEncodingException, JSONException {
        HashMap<String, ParamDescription> params = new HashMap<>();
        CqlRule rule = new CqlRule("LibraryOne", "1", "bGlicmFyeSBMaWJyYXJ5T25lIHZlcnNpb24gJzEnCgp1c2luZyBGSElSIHZlcnNpb24gJzQuMC4xJwoKaW5jbHVkZSBGSElSSGVscGVycyB2ZXJzaW9uICc0LjAuMScKCmNvbnRleHQgUGF0aWVudAoKZGVmaW5lIHg6IFBhdGllbnQuZ2VuZGVyID0gJ2ZlbWFsZSc/");
        ParamInjectionRequestBody input = new ParamInjectionRequestBody(params, rule, new HashMap<>());
        TranslationException thrown = assertThrows(
                TranslationException.class,
                () -> controller.translate(input),
                "Expected controller.translate() to throw TranslationException,  but it didn't"
        );
        assertEquals("CQL Translation error", thrown.getMessage());
    }
}
