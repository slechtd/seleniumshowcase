
## SeleniumShowcase

### About:

This is my portfolio project designed to showcase my approach to building a scalable and maintainable **Test Automation Solution using Java, Selenium Webdriver, TestNG, Maven** and supporting dependencies. It uses [automationteststore.com](https://automationteststore.com/) as a sample web application.

### Key features:
- Uses the **Page Object Model**. Common functionalities are abstracted away and inherited.
- Test runs can be **parametrised** (desired browser, environment, test suite, etc..) using Maven properties.
- Enabled for **parallel test executions**.
- Handles **advanced GUI interactions** such as scrolling and hovering over elements.
- Supports **data-driven testing** through .csv files.
- **Custom database logging** supports visualisation and test result analytics.
- Automatically handles **file separators** based on the OS.

### Project structure:
<pre>
seleniumshowcase/
├── out/
│   ├── reports/
│   └── screenshots/
├── pom.xml
├── readme.md
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── baseClasses/
    │   │   │   ├── BasePageObject.java
    │   │   │   └── BaseTestCase.java
    │   │   ├── pageObjects/
    │   │   │   ├── ContactUsPageObject.java
    │   │   │   ├── ContactUsSuccessPageObject.java
    │   │   │   ├── HomePageObject.java
    │   │   │   ├── LoginPageObject.java
    │   │   │   ├── LogoutPageObject.java
    │   │   │   └── MyAccountPageObject.java
    │   │   ├── testCases/
    │   │   │   ├── ContactUsTestCase.java
    │   │   │   └── LoginTestCase.java
    │   │   └── utils/
    │   │       ├── DBLogger.java
    │   │       ├── DatabaseConnector.java
    │   │       ├── EnvironmentManager.java
    │   │       ├── Listeners.java
    │   │       ├── PropertiesReader.java
    │   │       ├── ScreenshotManager.java
    │   │       ├── TestDataManager.java
    │   │       └── WebDriverFactory.java
    ├── resources/
        ├── drivers/
        │   └── chromedriver
        ├── testData/
        │   ├── contactUsInputs.csv
        │   └── loginCredentials.csv
        └── testSuites/
            ├── full-testng.xml
            └── smoke-testng.xml
</pre>

## SetupGuide:

### Prerequisites:

- JDK 17
- Maven
- A supported browser (Chrome, Edge, Firefox)

### To run locally:

1. Download drivers appropriate to your browser version ([Chrome](https://chromedriver.chromium.org/downloads),
   [Edge](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/), [Gecko](https://github.com/mozilla/geckodriver/releases))
2. Put the drivers in src/main/resources/drivers
3. Reload the Maven project to install dependencies.
4. Check pom.xml for additional settings.
5. Run src/main/resources/testSuites/full-testng.xml

## Feature breakdown:

### Browser, environment and other settings:
The project uses properties defined in pom.xml for browser, environment and other configurations. Modify these settings as needed.
```xml
<properties>
    <browser>chrome</browser>
    <environment>DEV</environment>
    <headless>false</headless>
    <suiteXmlFile>src/main/resources/testSuites/smoke-testng.xml</suiteXmlFile>
    <logIntoDB>false</logIntoDB>
    <dbHost>placeholder</dbHost>
    <dbUser>placeholder</dbUser>
    <dbPassword>placeholder</dbPassword>
</properties>
```

### Running through Jenkins:
These properties are also considered while setting up Jenkins goal/buildstep like so:
```sh
clean test -Dbrowser=edge -Denvironment=DEV -DsuiteXmlFile=src/main/resources/testSuites/full-testng.xml -Dheadless=false -DdbHost=placeholder -DdbUser=placeholder -DdbPassword=placeholder -DlogIntoDB=true
```
### Data-driven testing:

Test cases based on filling out forms, checking input validation, etc. are written in a data-agnostic manner. A data-driven approach is possible using .csv files as long as the general required format of the test data is adhered to. Example:

```csv
rowNr,firstName,firstNameValid,email,emailValid,enquiry,enquiryValid
1,Dan,true,dan.dan@dan.com,true,Lorem ipsu,true
2,D,false,dan.dan@dan.com,true,Lorem ipsu,true
3,Dan,true,dandandan.com,false,Lorem ipsu,true
4,Dan,true,dan.dan@dan.com,true,Lo,false
5,Dandandandandandandandandandandan,false,dan.dan@dan.com,true,Lorem ipsu,true
```

### Database logging and analytics:
Logging is handled through the DBLogger class. A direct connection to a DB can be established. While running locally, database logging can be disabled by adjusting the logIntoDB property in pom.xml. While running in Jenkins on a machine with a DB, the dbHost, dbUser and dbPassword properties should not be stored in the pom.xml (keep the default placeholder values), but rather real values should be passed in Maven Goals in a buildstep.

### Targed DB schema for DBLogger output:

```sql
CREATE TABLE executions (
    execution_id VARCHAR(255) PRIMARY KEY,
    test_suite VARCHAR(255),
    browser VARCHAR(50),
    environment VARCHAR(50),
    headless BOOLEAN,
    executionStartTimestamp DATETIME,
    executionEndTimestamp DATETIME
);

CREATE TABLE screenshot_paths (
    id SERIAL PRIMARY KEY,
    execution_id VARCHAR(255),
    test_case_name VARCHAR(255),
    path VARCHAR(255),
    FOREIGN KEY (execution_id) REFERENCES executions(execution_id)
);

CREATE TABLE test_logs (
    id SERIAL PRIMARY KEY,
    execution_id VARCHAR(255),
    test_case_name VARCHAR(255),
    message TEXT,
    log_type VARCHAR(50),
    timestamp DATETIME,
    FOREIGN KEY (execution_id) REFERENCES executions(execution_id)
);
```

### Example Grafana dashboard using the output data:

![Example Grafana Dashboard](GrafanaExample.png)
