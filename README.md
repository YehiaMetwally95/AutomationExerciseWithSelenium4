## Overveiw
- Automated Tests with Native Selenium 4 written by Java and based on Maven with simple script free of conditions, loops or complex logics
- Implement number of Best Practices either in Project Structure, Synchronizations and Validations
- Using TestNG as the Testing Framework
- Using Fluent Page Object Model Design Pattern in writing Test script and Page actions, thus chaining the Scenario steps and validations in one line of code
- Using Fluent Wait Strategy to Synchronize all actions on elements without any Thread Sleep
- Designing Powerful Util Class for Different Actions with Web Elements and Bot Pattern for Abstracting Successive interactions with Web Element
- Test Data Management such that store All Test data in Json Files and Retrieve the Updated Test Data of Products and Users from MySQL Database
- Generating Very Detailed Allure Reports with All Scenario Steps And Screenshots for Passed/Hard-Assertion-Failed/Soft-Assersion-Failed Tests and for API Requests and Responses
- Perform Test Execution On Local / Headless / Remotely using Selenium Grid with Docker Containers
- Performing Parallel Execution from CI/CD Pipeline with GitHub Actions, Supplied by Selenium Grid with Docker, To Run 15 Parallel Tests at same time, thus Reducing Execution time to reaches 90 seconds only to run all the 16 tests instead of 12 minutes when run sequentially
- Bypassing UI Login for All Tests, Thus Reducing Execution time
- Implementing the Test Automation Pyramid such that Run Tests Over API besides the UI, Thus Reduce the full Dependancy on Element Identifications and Allow Data Validations on API, Thus Reduce the possibility of Script failure, Reduce execution time, Reduce dependency on all system features to be working and Provide better test coverage
- Using Dynamic Locators for Identifying Tabular data instead of complicated xpath expressions
 
## Application Under Test
- Automation Exercise Website https://automationexercise.com/

## Installation
### Note : In Case of Local Execution and Without Sync from Database, You Don't have to Install Docker or Setup any of the Below 
#### 1- Docker Must be Installed and Run on your machine
#### 2- To Setup MySQL Database that store data of Products & Users, Just run the following command in Intellij Terminal
```bash
docker compose -f src/main/resources/dockerFiles/docker-compose-mysql-v3.yml up -d
```
- The .sql file is located in resources Directory, You can edit it using any IDE as MySQL Workbench
- To Sync The Tests With MySQL Database to get the Updated Products and Users Data, The flag "syncWithDB" in Configuration.properties shall be set with "true", otherwise, it can be set with "false" 
#### 3- To Setup Selenium Grid with Docker Container in order to Run Tests Remotely, Just run the following command in Intellij Terminal
```bash
docker compose -f src/main/resources/dockerFiles/docker-compose-grid-v3.yml up --scale chrome=2 --scale edge=0 --scale firefox=0 -d 
```
- After The Setup, To be Able to Run Remotely, The flags "executionType" & "isHeadless" in Configuration.properties shall be set with "Remote" & "true"
- After Finish the Test Execution, Its Better to CleanUp and Stop running the Docker Containers by running the following command in Terminal
```bash
docker compose -f src/main/resources/dockerFiles/docker-compose-grid-v3.yml down ; docker compose -f src/main/resources/docker-compose-mysql-v3.yml down 
```  

## Features
#### Structure of "main folder"
- Using the HomePage as Parent of all pages that inherit locators and actions of Header & Footer from Homepage also for defining common variables that are commonly used across all pages, Thus achieving the right purpose of Inheritance
- Synchronizing the Elements identification and Actions on elements inside Fluent Wait with lambda expression, thus waiting till action is taken not just till finding the element
- Using Bot Pattern as layer of abstraction for multiple interactions with web elements such as type (wait till textBox is located & clear textBox & write on textBox & take screenshot for textBox & print what is written on textBox)
- Implement Util for different Actions with Web Elements / Scrolling / Alerts
- Finding Elements using simple Techniques with ID, CSS Selectors & XPath & Advanced Techniques like XPath Axis and Relative Locators"
- Provide Click using Javascript Only In case the Webdriver is tried to Click till the Fluent wait timeout then failed
- Implement Utils for JDBC Manager & Json Files Manager & Properties File Manager & APIs Manager

#### Structure of "test folder"
- Using Base Test Class for defining Annotations to Open and Close Browser, such that all Test Classes inherit from it
- Start each Test from a clean state by Setting and Tearing down Brower for Every Test Case
- Using TestNG Listeners, to perform actions before and after running every suite"
- "Using Assertions as follows:
   - All Assertions are implemented in Page Class to allow the Fluency of Scenarios Steps with Validations like (Navigate.Writesteps..SoftAssertions.HardAssertions)
   - Using Hard assertions after every test & Soft assertions for doing verifications within the test"
- Using Bypass UI Login Method by Login only once for first time and store Session data on Json file, then use them to Run all next Tests with already logged-in user without need to Perform Login before each test

#### Implementing Tests over API layer besides UI Tests to achieve the Automation Test Pyramid
- User Registeration is done over API, then Login is done over GUI only once, then Bypass login for all next Tests
- Search for Products is done over API
- Data Validation on Product Details is done over API
- Data Validation on Address /User Details is done over API
- Data Validation on all retrieved Products in Product Page over API instead of Validating them on GUI (Not Implemented yet)
- Implementing API Tests Using RestAssured and Design Patterns, refer to Readme File of RestAssured Project https://github.com/YehiaMetwally95/NotesApiWithRestassured/blob/master/README.md"

#### Reporting Using Allure Report
- Reporting Test Result & Taking Screenshots for Failed Tests and Successful Tests
- Reporting Soft assertion failures and Taking Screenshots for them 
- Reporting All API Requests and Responses Sent, with a Screenshot for each
- Logging All Scenario Steps and Test Validations, in form of main steps and expanding every main step to check its child steps
- Defining Epics/Features/Stories

#### Test Data Management
- Reading Global Variables and Configurations from Properties file, like selecting browser type, execution type, setting capabilities of every browser like headless execution, disable infobars, start maximized, etc
- Test Data Preparation for Live Data like ""UsersData , ProductsData"" by setting Json File for every Test Case which read its data from mysql database using JDBC , And then Before every run, any updates in database will be reflected into Json Files to be used in the next runs
- Test Data Preparation for Static Data like "Messages, Page Titles, Credit Card Details"" by Filling it Manually on Json Files for every Test & User this data for Validations
- Test Data Generation for User Data like ""Registeration Inputs"" using Time Stamp for Unique values & Data faker for more Descriptive values and store this data into Json Files to use it for Validation in Run time 
- Test Data Execution by reading test data from Json files whether Json data is represend as Simple Json Object or Nested Json Objects or Array of Json Objects
- After Every User Registeration, The script adds the created User and his details in the Database and then reflect the database updates to Json Files that store Users to be used in next runs

#### Perform Parallel Execution
- Implementing All Test Methods to be independant in their steps and each Test has its own data in separate Json File, to allow Parallel Execution in Method Level
- Using ThreadLocal for isolating the WebDriver and Enable it to run Parallel tests
- Scaling up Tests to run in Parallel across Different Browsers Remotely on by help of Docker Containers with Selenium Grid 4 on Hub and Node Mode

#### Using TestNG XML File to Trigger Tests (Locally/Remotey) & On (Different Browsers) & (Sequencially/ In Parallel)

#### Create CI/CD Pipeline with Github Actions
- Workflow that run all the 16 E2E Tests in Parallel within 90 seconds only, In 2-Tiers of Parallelization 
    - 1st Tier --> Parallel Execution across 6 Jobs, each one runs on different platforms (ubuntu/windows) and browsers (chrome/edge/firefox)
    - 2nd Tier --> Parallel Execution inside each Job with help of Selenium grid and Docker containers 
- Two Workflows, One to run Tests over UI and the other to run the Same Tests over API which is much faster
- Tests runs Locally or Remotely on Selenium Grid and Docker containers
- Run Workflows on Different Triggers: after every Push, Scheduled and Manually
- Generate the Allure Reports after every Workflow run such that 
    - Separate Reports for Tests related to a Single Job
    - Combined Report that Include All Tests that runs on all Jobs 

💥 Videos
Review Project Structure
https://lnkd.in/de5TC9Pb

Run Test (API+GUI) Locally on Chrome through XML File
https://lnkd.in/dvCyBnzA

Run Test with soft assertion failure, Remotely on Chrome through Selenium Grid with Docker Container
https://lnkd.in/d7aVYCuU

Run Regression Workflow of 16 Tests through Pipeline using GitHub actions in Parallel within 100 seconds
https://lnkd.in/dwrRDcvu

💥Allure Reports for Parallel Test (90 secs) & Sequential Test (8 mins)
https://lnkd.in/dderUEEn