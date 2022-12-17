# #Selenium Automation Framework

Selenium Automation Framework is a code structure that makes code maintenance easy and efficient.
Framework produces beneficial outcomes like increased code reusability, higher portability, reduced cost of
script maintenance, better code readability, etc. In this framework, hybrid driven framework is implemented in this
for better folder structure and code reusability. Below mentioned components are implemented in this framework.

1.Support for TDD (Test-driven approach with TestNG) and BDD (Behavior-driven development with Cucumber)

2.Page object model for modularity

3.Reporting (Extent, TestNG, and Cucumber)

4.Cross-browser testing

5.Parallel execution

6.Extensive logging

7.More than 200 wrapper classes to support the test cases creation

8.Mail-sending and reading utilities

9.XLS reading / writing

10.Framework documentation

## Automation Framework

In this framework,we used Java as a programing language and Selenium 4 as an automation tool for ui automation.

| programing language | Version |
|---------------------|---------|
| Java                | Above 8 |

##### Note : Java version need to be updated in [pom.xml](pom.xml) file according to the user Java version. In below-mentioned places need to update java version

<java.version>${version}</java.version>

<maven.compiler.source>${version}</maven.compiler.source>

<maven.compiler.target>${version}</maven.compiler.target>

### Selenium Testng Framework

In Testng framework, Selenium is used as automation tool and testng is used for assertions. Extent report/Allure report
is used for
report generation. Maven is used as build tool to build a project.Data driven framework also implemented to read data
from excel and pass that data to test cases

#### Build tool - Maven

#### Framework/tool versions

| Framework/tool | Version |
|----------------|---------|
| Selenium       | 4.4.0   |
| Testng         | 7.6.1   |
| Extent Report  | 5.0.9   |
| poi-ooxml      | 5.2.2   |
| Allure-testng  | 2.19.0  |
| poi            | 5.2.2   |

By using the Maven Build tool, User can run the tests through command line

#### Maven command to resolve dependencies

`mvn dependency:resolve`

#### Basic Maven command to execute Scenarios

`mvn clean test`

### Selenium Cucumber framework

In this project,we used Java as a programing language and Selenium 4 as an automation tool for ui automation.
Cucumber-Testng
framework is integrated
in this framework because of Intuitive Way to Express Requirements in Human-Readable Form for testing. Cucumber-Testng
framework
is also integrated in this for Assertions. Cucumber Extent Report is used for report generation.

#### Framework/tool versions

| Framework/tool                  | Version |
|---------------------------------|---------|
| Selenium                        | 4.4.0   |
| Cucumber                        | 7.6.0   |
| Testng                          | 7.6.1   |
| cucumber-reporting              | 5.7.2   |
| cucumber-picocontainer          | 7.6.0   |
| Cucumber-Testng                 | 7.6.0   |
| extentreports-cucumber7-adapter | 1.7.0   |
| cucumber-java                   | 7.6.0   |

By using the Maven Build tool, User can run the Scenarios through command line

#### Maven command to resolve dependencies

`mvn dependency:resolve`

#### Basic Maven command to execute Scenarios

`mvn clean test`

#### Maven Command to execute the Cucumber Scenarios by using tags

`mvn clean test "-DargLine=-Dcucumber.filter.tags='@${tags}'"`

Example: mvn clean test "-DargLine=-Dcucumber.filter.tags='@E2E'"

## Common plugins for Testng and Cucumber framework

| Framework/plugin | Version  |
|------------------|----------|
| webDriverManager | 5.2.3    |
| log4j2           | 2.19.0   |
| commons          | 2.11.0   |
| javax.mail       | 1.6.2    |
| mysql-connector  | 8.0.30   |
| json             | 20220924 |
