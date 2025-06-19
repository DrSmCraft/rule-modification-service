# rule-modification-service

A service for modifying and translating CQL rules programmatically. This project is built to accomodate
the [CDSS4PCP](https://cdss4pcp.com/) project.

## Running `rule-modification-service` with Docker Compose

### 1. **Ensure prerequisites are installed**

* [Docker](https://docs.docker.com/get-docker/)
* [Docker Compose](https://docs.docker.com/compose/install/)

---

### 2. **Clone the repository**

```bash
git clone https://github.com/DrSmCraft/rule-modification-service.git
cd rule-modification-service
```

---

### 3. **Build and run the service**

```bash
docker-compose up --build
```

Once started, the service should be accessible at:
[`http://localhost:9090`](http://localhost:9090)

---

### 4. **View Documentation**

See the Swagger UI OpenAPI documention at
[`http://localhost:9090/docs`](http://localhost:9090/docs/)

## Features

1. Translate CQL to ELM with the following flags (not all were tested):
    * disableDefaultModelInfoLoad
    * verify
    * optimization
    * annotations
    * locators
    * resultTypes
    * detailedErrors
    * errorLevel
    * disableListTraversal
    * disableListDemotion
    * disableListPromotion
    * enableIntervalDemotion
    * enableIntervalPromotion
    * disableMethodInvocation
    * requireFromKeyword
    * strict
    * validateUnits
    * signatures
    * compatibilityLevel
2. Inject values into parameter expressions in CQL for the following data types:
    * Boolean: found
      at [ParamBooleanBuilder.java](src/main/java/com/cdss4pcp/rulemodificationservice/parambuilder/ParamBooleanBuilder.java)
    * String: found
      at [ParamStringBuilder.java](src/main/java/com/cdss4pcp/rulemodificationservice/parambuilder/ParamStringBuilder.java)
    * Integer: found
      at [ParamIntegerBuilder.java](src/main/java/com/cdss4pcp/rulemodificationservice/parambuilder/ParamIntegerBuilder.java)
    * More DataTypes can be added by:
        1. Adding a class for the datatype Param Builder and
           extending [IParamBuilder.java](src/main/java/com/cdss4pcp/rulemodificationservice/parambuilder/IParamBuilder.java)
        2. Implement `buildPattern(String param)` and `buildReplacement(String param, T value)`
        3. Register your new Param Builder
           in [ParamInjectorService.java](src/main/java/com/cdss4pcp/rulemodificationservice/parambuilder/ParamInjectorService.java).
           For example,
           ```java
           private void postConstruct() {
             paramBuilders = new HashMap<>();
             paramBuilders.put("Integer", new ParamIntegerBuilder());
             paramBuilders.put("String", new ParamStringBuilder());
             paramBuilders.put("Boolean", new ParamBooleanBuilder());
                               
             // Your new ParamBuilder for new Datatype
             paramBuilders.put("YourDataType", new ParamYourDataTypeBuilder());
             }
             ```
        4. Add logic to process your datatype
           in [ParamInjectorService.java](src/main/java/com/cdss4pcp/rulemodificationservice/parambuilder/ParamInjectorService.java),
           by adding another clause in
           `String injectParameters(HashMap<String, ParamDescription> params, String cql)`. For
           example,
           ```java
            public String injectParameters(HashMap<String, ParamDescription> params, String cql) {
                ...
                               
                if (value.type.equalsIgnoreCase("Integer")) {
                    ...
                } 
                else if (value.type.equalsIgnoreCase("String")) {
                    ...
                } 
                else if (value.type.equalsIgnoreCase("Boolean")) {
                   ...
                }
                else if (value.type.equalsIgnoreCase("YouNewDataType")) { // Add this clause
                   // You logic for processing your datatype here
                }
                else {
                    throw new RuntimeException("Unrecognized type: " + value.type);
                }
            }
            return newCQL;
           }
        ```
3. Modify the library name and library version of CQL to new requested values
