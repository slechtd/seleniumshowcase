## Project Setup Guide

### About

This is a portfolio project designed to showcase my approach to building a scalable and maintainable test automation framework. Developed in Java, the framework leverages Selenium WebDriver for browser automation and TestNG for test case management. The framework is optimized for parallel or multithreaded test executions, increasing efficiency and speed. Although the test cases are designed to run against the sample website automationteststore.com, the primary aim is not exhaustive testing of the site. Instead, the focus is on demonstrating a structured framework that adheres to the Page Object Model (POM) for enhanced reusability and maintainability. The framework supports data-driven testing through Excel spreadsheets and is equipped for multi-environment testing. Logging has been upgraded from Extent Reports to custom logging directly into a database. This change allows for real-time analytics and visualizations via Grafana, offering a more dynamic and interactive reporting experience.

### Prerequisites

- Java JDK 17
- Maven
- A supported browser (Chrome, Edge, Firefox)

### Setup to run locally

1. Download drivers appropriate to your browser, browser version and OS
   - https://chromedriver.chromium.org/downloads
   - https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
   - https://github.com/mozilla/geckodriver/releases
2. Put the drivers into src/main/resources/drivers
3. Reload the Maven project to install dependencies
4. Set the browser you wish to test in (Chrome by default) by adjusting the browser property in pom.xml
5. Run the test suite by running src/main/resources/testSuites/full-testng.xml

### Running through Jenkins:

clean test -Dbrowser=edge -Denvironment=DEV -DsuiteXmlFile=src/main/resources/testSuites/full-testng.xml -Dheadless=false -DdbHost=placeholder -DdbUser=placeholder -DdbPassword=placeholder -DlogIntoDB=true

### Database logging:

Logging is handles trough the MiniLogger class. A direct connection to a DB can be established. While running locally, database logging can be disabled by adjusting the logIntoDB property in pom.xml. While running in Jenkins, the dbHost, dbUser and dbPassword properties should not be stored in the pom.xml (keep the default placeholder values), but rather read values should be passed in Maven Goals in a buildstep.

### Targed DB schema for MiniLogger output:

The output data can then be easily visualised and analysed in Grafana, PowerBI...

<pre>
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
</pre>

