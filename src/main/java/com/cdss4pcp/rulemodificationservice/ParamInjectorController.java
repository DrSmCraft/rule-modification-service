package com.cdss4pcp.rulemodificationservice;

import com.cdss4pcp.rulemodificationservice.parambuilder.ParamInjectorService;
import com.cdss4pcp.rulemodificationservice.util.CqlRule;
import com.cdss4pcp.rulemodificationservice.util.ParamInjectionLibrarySourceProvider;
import com.cdss4pcp.rulemodificationservice.util.ParamInjectionUtil;
import com.cdss4pcp.rulemodificationservice.util.TranslationException;
import org.cqframework.cql.cql2elm.*;
import org.cqframework.cql.cql2elm.quick.FhirLibrarySourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController()
public class ParamInjectorController {

    @Value("${rule-modification-service.injectUrl}")
    private String injectUrl;
    @Value("${rule-modification-service.translateUrl}")
    private String translateUrl;
    @Value("${rule-modification-service.injectTranslateUrl}")
    private String injectTranslateUrl;

    private Logger logger = LoggerFactory.getLogger(ParamInjectorController.class);

    @Autowired
    protected ParamInjectorService injectorService = new ParamInjectorService();

    protected ModelManager modelManager;

    ParamInjectorController() {
        modelManager = new ModelManager();
    }


    /**
     * Injects parameters into a CQL rule and returns a new ParamInjectionRequestBody with the updated CQL rule.
     *
     * @param body The request body containing the CQL rule and libraries to be injected
     * @return A new ParamInjectionRequestBody object with the injected parameters
     * @throws UnsupportedEncodingException If an unsupported encoding is encountered during the injection process
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.OPTIONS}, path = "${rule-modification-service.injectUrl}", produces = "application/json")
    public ParamInjectionRequestBody inject(@RequestBody ParamInjectionRequestBody body) throws UnsupportedEncodingException {
        CqlRule rule = body.getRule();
        checkParamInjectionRequestBody(body);

        String decodedCql = ParamInjectionUtil.decodeCql(rule.getEncodedCql());
        String newCql = injectorService.injectParameters(body.getParams(), decodedCql);

        String encodedCql = ParamInjectionUtil.encodeCql(newCql);


        return new ParamInjectionRequestBody(body.getParams(), new CqlRule(rule.getId(), rule.getVersion(), encodedCql), body.getLibraries());

    }


    /**
     * Translates the given CQL rule using the provided parameters and options.
     *
     * @param body                        The request body containing the CQL rule and libraries to be translated
     * @param disableDefaultModelInfoLoad Flag to disable default model info loading
     * @param verify                      Flag to enable verification
     * @param optimization                Flag to enable optimization
     * @param annotations                 Flag to include annotations
     * @param locators                    Flag to include locators
     * @param resultTypes                 Flag to include result types
     * @param detailedErrors              Flag to include detailed errors
     * @param errorLevel                  The severity level of errors
     * @param disableListTraversal        Flag to disable list traversal
     * @param disableListDemotion         Flag to disable list demotion
     * @param disableListPromotion        Flag to disable list promotion
     * @param enableIntervalDemotion      Flag to enable interval demotion
     * @param enableIntervalPromotion     Flag to enable interval promotion
     * @param disableMethodInvocation     Flag to disable method invocation
     * @param requireFromKeyword          Flag to require 'from' keyword
     * @param strict                      Flag to enforce strict type checking
     * @param validateUnits               Flag to validate units
     * @param signatures                  The signature level for library builder
     * @param compatibilityLevel          The compatibility level for the translation, valid values are 1.3, 1.4, and 1.5
     * @return The translated CQL rule in JSON format
     * @throws UnsupportedEncodingException If an unsupported encoding is encountered
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.OPTIONS}, path = "${rule-modification-service.translateUrl}", produces = "application/json")
    String translate(@RequestBody ParamInjectionRequestBody body, @RequestParam(required = false) Boolean disableDefaultModelInfoLoad, @RequestParam(required = false) Boolean verify, @RequestParam(required = false) Boolean optimization, @RequestParam(required = false) Boolean annotations, @RequestParam(required = false) Boolean locators, @RequestParam(required = false) Boolean resultTypes, @RequestParam(required = false) Boolean detailedErrors, @RequestParam(required = false) CqlCompilerException.ErrorSeverity errorLevel, @RequestParam(required = false) Boolean disableListTraversal, @RequestParam(required = false) Boolean disableListDemotion, @RequestParam(required = false) Boolean disableListPromotion, @RequestParam(required = false) Boolean enableIntervalDemotion, @RequestParam(required = false) Boolean enableIntervalPromotion, @RequestParam(required = false) Boolean disableMethodInvocation, @RequestParam(required = false) Boolean requireFromKeyword, @RequestParam(required = false) Boolean strict, @RequestParam(required = false) Boolean validateUnits, @RequestParam(required = false) LibraryBuilder.SignatureLevel signatures, @RequestParam(required = false) String compatibilityLevel) throws UnsupportedEncodingException {
        checkParamInjectionRequestBody(body);
        CqlCompilerOptions options;
        boolean noOptionsGiven = optimization == null && annotations == null && locators == null && resultTypes == null && verify == null && detailedErrors == null && errorLevel == null && disableListTraversal == null && disableListDemotion == null && disableListPromotion == null && enableIntervalDemotion == null && enableIntervalPromotion == null && disableMethodInvocation == null && requireFromKeyword == null && validateUnits == null && disableDefaultModelInfoLoad == null && compatibilityLevel == null && strict == null;
        if (noOptionsGiven) {
            options = new CqlCompilerOptions();
        } else {
            options = new CqlCompilerOptions(
                    optimization != null ? optimization : false,
                    annotations != null ? annotations : true,
                    locators != null ? locators : true,
                    resultTypes != null ? resultTypes : false,
                    verify != null ? verify : false,
                    detailedErrors != null ? detailedErrors : false, // Didn't include in debug, maybe should...
                    errorLevel != null ? errorLevel : CqlCompilerException.ErrorSeverity.Info,
                    strict == null ? (disableListTraversal != null ? disableListTraversal : true) : strict,
                    strict == null ? (disableListDemotion != null ? disableListDemotion : true) : strict,
                    strict == null ? (disableListPromotion != null ? disableListPromotion : true) : strict,
                    enableIntervalDemotion != null ? enableIntervalDemotion : false,
                    enableIntervalPromotion != null ? enableIntervalPromotion : false,
                    strict == null ? (disableMethodInvocation != null ? disableMethodInvocation : true) : strict,
                    requireFromKeyword != null ? requireFromKeyword : false,
                    validateUnits != null ? validateUnits : false,
                    disableDefaultModelInfoLoad != null ? disableDefaultModelInfoLoad : false,
                    signatures,
                    compatibilityLevel);

        }


        LibraryManager libraryManager = new LibraryManager(modelManager, options);
        libraryManager.getLibrarySourceLoader().registerProvider(new FhirLibrarySourceProvider());


        try {
            URL url = this.getClass().getClassLoader().getResource("libraries");
            File f = new File(url.toURI());
            Path path = f.toPath();
            libraryManager.getLibrarySourceLoader().registerProvider(new DefaultLibrarySourceProvider(path));
        } catch (Exception e) {
            logger.error("Error encountered while loading libraries from file, will attempt to continue without them", e);
        }
        if (body.getLibraries() != null && !body.getLibraries().isEmpty()) {
            libraryManager.getLibrarySourceLoader().registerProvider(new ParamInjectionLibrarySourceProvider(body.getLibraries()));
        }
        String cql = ParamInjectionUtil.decodeCql(body.getRule().getEncodedCql());

        CqlTranslator translator = CqlTranslator.fromText(cql, libraryManager);

        libraryManager.getCqlCompilerOptions();
        libraryManager.getLibrarySourceLoader().clearProviders();


        if (translator.getErrors().size() > 0) {
            throw new TranslationException(translator.getErrors());
        }


        return translator.toJson();


    }

    /**
     * Helper function for unit tests that calls translate() with all flags null
     *
     * @param body The request body containing the CQL rule and libraries to be translated
     * @return The translated CQL rule in JSON format
     * @throws UnsupportedEncodingException If an unsupported encoding is encountered
     */
    String translate(ParamInjectionRequestBody body) throws UnsupportedEncodingException {
        return translate(body, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Injects parameters into a CQL rule and translates the given CQL rule using the provided parameters and options.
     *
     * @param body                        The request body containing the CQL rule and libraries to be translated
     * @param disableDefaultModelInfoLoad Flag to disable default model info loading
     * @param verify                      Flag to enable verification
     * @param optimization                Flag to enable optimization
     * @param annotations                 Flag to include annotations
     * @param locators                    Flag to include locators
     * @param resultTypes                 Flag to include result types
     * @param detailedErrors              Flag to include detailed errors
     * @param errorLevel                  The severity level of errors
     * @param disableListTraversal        Flag to disable list traversal
     * @param disableListDemotion         Flag to disable list demotion
     * @param disableListPromotion        Flag to disable list promotion
     * @param enableIntervalDemotion      Flag to enable interval demotion
     * @param enableIntervalPromotion     Flag to enable interval promotion
     * @param disableMethodInvocation     Flag to disable method invocation
     * @param requireFromKeyword          Flag to require 'from' keyword in queries
     * @param strict                      Flag to enforce strict type checking
     * @param validateUnits               Flag to validate units
     * @param signatures                  The signature level for library builder
     * @param compatibilityLevel          The compatibility level for the translation, valid values are 1.3, 1.4, and 1.5
     * @return The translated CQL rule in JSON format
     * @throws UnsupportedEncodingException If an unsupported encoding is encountered
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.OPTIONS}, path = "${rule-modification-service.injectTranslateUrl}", produces = "application/json")
    String injectAndTranslate(@RequestBody ParamInjectionRequestBody body, @RequestParam(required = false) Boolean disableDefaultModelInfoLoad, @RequestParam(required = false) Boolean verify, @RequestParam(required = false) Boolean optimization, @RequestParam(required = false) Boolean annotations, @RequestParam(required = false) Boolean locators, @RequestParam(required = false) Boolean resultTypes, @RequestParam(required = false) Boolean detailedErrors, @RequestParam(required = false) CqlCompilerException.ErrorSeverity errorLevel, @RequestParam(required = false) Boolean disableListTraversal, @RequestParam(required = false) Boolean disableListDemotion, @RequestParam(required = false) Boolean disableListPromotion, @RequestParam(required = false) Boolean enableIntervalDemotion, @RequestParam(required = false) Boolean enableIntervalPromotion, @RequestParam(required = false) Boolean disableMethodInvocation, @RequestParam(required = false) Boolean requireFromKeyword, @RequestParam(required = false) Boolean strict, @RequestParam(required = false) Boolean validateUnits, @RequestParam(required = false) LibraryBuilder.SignatureLevel signatures, @RequestParam(required = false) String compatibilityLevel) throws UnsupportedEncodingException {
        checkParamInjectionRequestBody(body);
        ParamInjectionRequestBody injectedRequest = inject(body);
        return translate(injectedRequest, disableDefaultModelInfoLoad, verify, optimization, annotations, locators, resultTypes, detailedErrors, errorLevel, disableListTraversal, disableListDemotion, disableListPromotion, enableIntervalDemotion, enableIntervalPromotion, disableMethodInvocation, requireFromKeyword, strict, validateUnits, signatures, compatibilityLevel);
    }


    /**
     * Checks the validity of the provided ParamInjectionRequestBody object.
     * Throws exceptions if the CQL rule or libraries are null or have missing/empty fields.
     */
    private void checkParamInjectionRequestBody(ParamInjectionRequestBody body) {
        if (body.getRule() == null) throw new NullPointerException("rule is null, please provide a valid rule");
        if (body.getRule().getId() == null || body.getRule().getId().isEmpty())
            throw new NullPointerException("rule.id is null or empty, please provide a valid id string");
        if (body.getRule().getVersion() == null || body.getRule().getVersion().isEmpty())
            throw new NullPointerException("rule.version is null or empty, please provide a valid version string");
        if (body.getRule().getEncodedCql() == null || body.getRule().getEncodedCql().isEmpty())
            throw new NullPointerException("rule.content is null or empty, please provide a valid Base64 encoded CQL rule");

        if (body.getLibraries() != null) {
            for (Map.Entry<String, CqlRule> entry : body.getLibraries().entrySet()) {
                CqlRule rule = entry.getValue();
                String name = entry.getKey();
                if (rule == null)
                    throw new NullPointerException(String.format("libraries[\"%s\"] is null, please provide a valid rule", name));
                if (rule.getId() == null || rule.getId().isEmpty())
                    throw new NullPointerException(String.format("libraries[\"%s\"].id is null or empty, please provide a valid id string", name));
                if (rule.getVersion() == null || rule.getVersion().isEmpty())
                    throw new NullPointerException(String.format("libraries[\"%s\"].version is null or empty, please provide a valid version string", name));
                if (rule.getEncodedCql() == null || rule.getEncodedCql().isEmpty())
                    throw new NullPointerException(String.format("libraries[\"%s\"].content is null or empty, please provide a valid Base64 encoded CQL rule", name));
            }

        }
    }


    /**
     * Handles NullPointerException by creating a response with timestamp, error code, error type, and message.
     * Determines the method name that caused the exception and adds the corresponding path to the response.
     *
     * @param e The NullPointerException that occurred
     * @return ResponseEntity with details of the error response
     */
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException e) {

        HashMap<String, Object> resp = new HashMap<>();
        resp.put("timestamp", new Date());
        resp.put("code", HttpStatus.BAD_REQUEST.value());
        resp.put("error", "Null Pointer");
        resp.put("message", e.getMessage());

        String currentClassName = ParamInjectorController.class.getName();
        int i;
        for (i = 0; i < e.getStackTrace().length; i++) {
            if (!e.getStackTrace()[i].getClassName().equals(currentClassName)) {
                break;
            }
        }
        if (i >= 0 && i < e.getStackTrace().length) {
            String methodName = e.getStackTrace()[i - 1].getMethodName();
            if (methodName.equals("inject")) {
                resp.put("path", injectUrl);
            } else if (methodName.equals("translate")) {
                resp.put("path", translateUrl);
            } else if (methodName.equals("injectAndTranslate")) {
                resp.put("path", injectTranslateUrl);
            }
        }

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles IllegalArgumentException by creating a response with timestamp, error code, error type, and message.
     * Checks the class name in the stack trace to determine the specific error scenario.
     * If the class name is 'java.util.Base64$Decoder', sets the error as 'Invalid Base64 encoded CQL rule'.
     * If the class name is 'java.lang.Enum', sets the error as 'Invalid value given'.
     *
     * @param e The IllegalArgumentException that occurred
     * @return ResponseEntity with details of the error response
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {

        HashMap<String, Object> resp = new HashMap<>();
        resp.put("timestamp", new Date());
        resp.put("code", HttpStatus.BAD_REQUEST.value());

        if (e.getStackTrace()[0].getClassName().equals("java.util.Base64$Decoder")) {
            resp.put("message", e.getMessage());
            resp.put("error", "Invalid Base64 encoded CQL rule");
        } else if (e.getStackTrace()[0].getClassName().equals("java.lang.Enum")) {
            resp.put("message", e.getMessage());
            resp.put("error", "Invalid value given");
        }


        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UnsupportedEncodingException by creating a response with timestamp, error code, and error message.
     * Checks if the exception's stack trace class name is 'java.util.Base64$Decoder' and sets the error message accordingly.
     *
     * @param e The UnsupportedEncodingException that occurred
     * @return ResponseEntity with details of the error response
     */
    @ExceptionHandler({UnsupportedEncodingException.class})
    public ResponseEntity<Map<String, Object>> handleUnsupportedEncodingException(UnsupportedEncodingException e) {

        HashMap<String, Object> resp = new HashMap<>();
        resp.put("timestamp", new Date());
        resp.put("code", HttpStatus.BAD_REQUEST.value());

        if (e.getStackTrace()[0].getClassName().equals("java.util.Base64$Decoder")) {
            resp.put("message", e.getMessage());
            resp.put("error", "Unsupported Encoding, use UTF-8 instead");
        }


        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles TranslationException by creating a response with timestamp, error code, message, and errors list.
     *
     * @param e The TranslationException that occurred
     * @return ResponseEntity with details of the error response
     */
    @ExceptionHandler({TranslationException.class})
    public ResponseEntity<Map<String, Object>> handleTranslationException(TranslationException e) {

        HashMap<String, Object> resp = new HashMap<>();
        resp.put("timestamp", new Date());
        resp.put("code", HttpStatus.BAD_REQUEST.value());
        resp.put("message", e.getMessage());
        resp.put("errors", e.getErrors());


        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }


}
