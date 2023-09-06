## Project Setup Guide

### About

This is a portfolio project meant to demonstrate my approach towards building a maintainable test automation framework. It is built on Java and utilizes Selenium WebDriver for browser automation and TestNG for test case management. The framework is designed to run against the sample website automationteststore.com, but it is not intended to provide exhaustive testing of the site. Instead, the focus is on showcasing a structured framework that follows the Page Object Model (POM) for reusability and maintainability. It supports data-driven testing (reading Excel spreadsheets), multi-environment testing, and includes utility classes for WebDriver and environment setup. Logging is handled through TestNG listeners, and Extent Reports provide test reporting.

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
4. Set the browser you wish to test in (Chrome by default) by adjusting src/main/resources/configs/config.properties
5. Run the test suite by running src/main/resources/testSuites/full-testng.xml
8. See test reports in out/reports

### Running through Jenkins:

clean test -Dbrowser=edge -Denvironment=DEV -DsuiteXmlFile=src/main/resources/testSuites/full-testng.xml -Dheadless=false -DdbHost=placeholder -DdbUser=placeholder -DdbPassword=placeholder -DlogIntoDB=true

