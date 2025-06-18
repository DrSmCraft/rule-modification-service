package com.cdss4pcp.rulemodificationservice.parambuilder;

import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class for injecting parameters into a given CQL rule based on their types.
 * Initializes param builders for Integer and String types.
 */
public class ParamInjectorService {

    HashMap<String, IParamBuilder> paramBuilders = new HashMap<>();

    public ParamInjectorService() {
        paramBuilders.put("Integer", new ParamIntegerBuilder());
        paramBuilders.put("String", new ParamStringBuilder());
        paramBuilders.put("Boolean", new ParamBooleanBuilder());
    }

    @PostConstruct
    private void postConstruct() {
        paramBuilders = new HashMap<>();
        paramBuilders.put("Integer", new ParamIntegerBuilder());
        paramBuilders.put("String", new ParamStringBuilder());
        paramBuilders.put("Boolean", new ParamBooleanBuilder());
    }

    /**
     * Injects parameters into the given CQL rule based on their types.
     *
     * @param params a HashMap containing parameter descriptions
     * @param cql    the CQL rule to inject parameters into
     * @return the modified CQL rule after parameter injection as a String
     * @throws RuntimeException if an unrecognized parameter type is encountered
     */
    public String injectParameters(HashMap<String, ParamDescription> params, String cql) {
        if (params == null) {
            return cql;
        }
        String newCQL = cql;
        for (HashMap.Entry<String, ParamDescription> entry : params.entrySet()) {
            ParamDescription value = entry.getValue();
            String param = entry.getKey();

            if (value.type.equalsIgnoreCase("Integer")) {
                Pattern pattern = paramBuilders.get("Integer").buildPattern(param);
                Integer val;
                try {
                    val = Integer.parseInt(value.value + "");
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Expected Integer but got a " + value.value.getClass() + " with value of " + value.value);
                }
                String replacement = paramBuilders.get("Integer").buildReplacement(param, val);
                Matcher matcher = pattern.matcher(newCQL);
                newCQL = matcher.replaceAll(replacement);
            }
            else if (value.type.equalsIgnoreCase("String")) {
                Pattern pattern = paramBuilders.get("String").buildPattern(param);
                String val;
                try {
                    val = value.value + "";
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Expected String but got a " + value.value.getClass() + " with value of " + value.value);
                }
                String replacement = paramBuilders.get("String").buildReplacement(param, val);
                Matcher matcher = pattern.matcher(newCQL);
                newCQL = matcher.replaceAll(replacement);
            }
            else if (value.type.equalsIgnoreCase("Boolean")) {
                Pattern pattern = paramBuilders.get("Boolean").buildPattern(param);
                Boolean val;
                try {
                    val = (Boolean) value.value;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Expected Boolean but got a " + value.value.getClass() + " with value of " + value.value);
                }
                String replacement = paramBuilders.get("Boolean").buildReplacement(param, val);
                Matcher matcher = pattern.matcher(newCQL);
                newCQL = matcher.replaceAll(replacement);
            }

            else {
                throw new RuntimeException("Unrecognized type: " + value.type);
            }
        }
        return newCQL;


    }


    public String injectNewLibraryNameAndVersion(String newLibraryName, String newLibraryVersion, String cql) {
        if (newLibraryName == null && newLibraryVersion == null) {
            return cql;
        }
        final Pattern libraryNameVersionPattern = Pattern.compile("[\s]*library[\s]*(\")?([A-Za-z]|'_')([A-Za-z0-9]|'_')*(\")?[\s]*version[\s]*(\')?(.*)(\')?");
        final Pattern libraryNamePattern = Pattern.compile("[\s]*library[\s]*(\")?([A-Za-z]|'_')([A-Za-z0-9]|'_')*(\")?");

        String originalLibraryName = null;
        String originalLibraryVersion = null;
        // Parse original library name and version
        Matcher matcher1 = libraryNameVersionPattern.matcher(cql);
        Matcher matcher2 = libraryNamePattern.matcher(cql);
        boolean foundNameAndVersion = matcher1.find();
        boolean foundName = matcher2.find();
        if (foundNameAndVersion) {
            String nameGroup = matcher2.group();
            nameGroup = nameGroup.trim();
            int last = nameGroup.lastIndexOf(" ");
            originalLibraryName = nameGroup.substring(last + 1).replace("'", "").replace("\"", "");

            String entireGroup = matcher1.group();
            String versionGroup = entireGroup.replace(nameGroup, "").trim();
            last = versionGroup.lastIndexOf(" ");
            originalLibraryVersion = versionGroup.substring(last + 1).replace("'", "").replace("\"", "");
        } else if (foundName) {
            String nameGroup = matcher2.group();
            nameGroup = nameGroup.trim();
            int last = nameGroup.lastIndexOf(" ");
            originalLibraryName = nameGroup.substring(last + 1).replace("'", "").replace("\"", "");

        }


        String newCQL;

        if (newLibraryName != null) {
            newLibraryName = newLibraryName.trim();
            Pattern namePattern = Pattern.compile("^(\")?([A-Za-z]|'_')([A-Za-z0-9]|'_')*(\")?$");
            Matcher nameMatcher = namePattern.matcher(newLibraryName);
            boolean nameValid = nameMatcher.find();
            int oldLibraryNameLength = newLibraryName.length();
            newLibraryName = newLibraryName.replace("\"", "");

            if (!nameValid || (oldLibraryNameLength - newLibraryName.length() != 0 && oldLibraryNameLength - newLibraryName.length() != 2)) {
                throw new IllegalArgumentException("Invalid library name: " + newLibraryName);
            }
        }


        if (newLibraryVersion != null) {
            newLibraryVersion = newLibraryVersion.trim();
            Pattern versionPattern = Pattern.compile("^(\')?(.*)(\')?$");
            Matcher versionMatcher = versionPattern.matcher(newLibraryVersion);
            boolean versionValid = versionMatcher.find();
            int oldVersionLength = newLibraryVersion.length();
            newLibraryVersion = newLibraryVersion.replace("'", "");

            if (!versionValid || (oldVersionLength - newLibraryVersion.length() != 0 && oldVersionLength - newLibraryVersion.length() != 2)) {
                throw new IllegalArgumentException("Invalid version: " + newLibraryVersion);
            }
        }


        matcher1 = libraryNameVersionPattern.matcher(cql);
        matcher2 = libraryNamePattern.matcher(cql);
        foundNameAndVersion = matcher1.find();
        foundName = matcher2.find();
        String nameGroup = matcher2.group();

        if (foundNameAndVersion) {
            if (newLibraryName == null) {
                newLibraryName = originalLibraryName;
            }
            if (newLibraryVersion == null) {
                newLibraryVersion = originalLibraryVersion;
            }
            newCQL = matcher1.replaceAll(String.format("library \"%s\" version '%s'", newLibraryName, newLibraryVersion));
        } else if (foundName) {
            if (newLibraryName == null) {
                newLibraryName = originalLibraryName;
            }
            newCQL = matcher2.replaceAll(String.format("library \"%s\"", newLibraryName));
        } else {
            newCQL = cql;
        }


        return newCQL;


    }

}
