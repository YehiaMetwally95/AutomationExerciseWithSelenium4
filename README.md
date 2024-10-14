## Overveiw
- Test Automation Script with Selenium 4 by Java and Maven Project to simulate E2E User Scenarios
- Implement number of Best Practices either in Project Structure, Synchronizations and Validations
- Using TestNG as the Testing Framework
- Using Fluent Page Object Model Design Pattern in writing Test script and Page actions, thus chaining the Scenario steps and validations in one line of code
- Using Fluent Wait Strategy to Synchronize all actions in the project and without any Thread Sleep
- Designing Powerful Util Class for Different Actions with Web Elements and Bot Pattern for Abstracting Multiple interactions with Web Element
- Data Driven framework such that store All Test data in Json Files and Retrieve the Updated Test Data of Products and Users from MySQL Database
- Generating Very Detailed Allure Reports with All Scenario Steps And Screenshots for Passed/Hard-Assertion-Failed/Soft-Assersion-Failed Tests, For better communicating the test results
- Perform Test Execution On Local / Headless / Remotely using Selenium Grid with Docker Containers
- Performing Parallel Execution from CI/CD Pipeline with Github Actions, Supplied by Selenium Grid with Docker, To Run 12 Parallel Tests at same time, thus Reducing Execution time to reaches 90 seconds only to run all the 14 tests
- Bypassing UI Login for All Tests, Thus Reducing Execution time
- Implementing the Test Automation Pyramid such that Run Tests Over API besides the UI, Thus Reduce the Full Dependancy on UI and Element Identification and Reduce Execution Time
  
## Application Under Test
- Automation Exercise Website https://automationexercise.com/

## Installation
### Note : In Case of Local Execution and Without Sync from Database, You Don't have to Install Docker or Setup any of the Below 
#### 1- Docker Must be Installed and Run on your machine and required images are downloaded
#### 2- To Setup MySQL Database that store data of Products & Users, Just run the following command in Intellij Terminal
```bash
docker run --name dockerDB -p 3306:3306 -e MYSQL_ROOT_PASSWORD=yehia -d mysql; Start-Sleep -Seconds 20; docker cp src/test/resources/DBFiles/ProductsAndUsers.sql dockerDB:/ProductsAndUsers.sql; docker exec -i dockerDB mysql -u root -p'yehia' -e "SOURCE /ProductsAndUsers.sql;" 
```
- The .sql file is located in resources Directory, You can edit it using any IDE as MySQL Workbench
- To Sync The Tests With MySQL Database to get the Updated Products and Users Data, The flag "syncWithDB" in Configuration.properties shall be set with "true", otherwise, it can be set with "false" 
#### 3- To Setup Selenium Grid with Docker Container in order to Run Tests Remotely, Just run the following command in Intellij Terminal
```bash
docker compose -f src/main/resources/docker-compose_grid-v3.yml up --scale chrome=5 --scale edge=5 --scale firefox=5 -d 
```
- After The Setup, To be Able to Run Remotely, The flags "executionType" & "isHeadless" in Configuration.properties shall be set with "Remote" & "true"
- After Finish the Test Execution, Its Better to CleanUp and Stop running the Docker Containers by running the following command in Terminal
```bash
docker compose -f src/main/resources/docker-compose_grid-v3.yml down ; docker stop dockerDB 
```  

## Features
#### Structure of "main folder"
- Using the HomePage as Parent of all pages that inherit locators and actions of Header & Footer from Homepage also for defining common variables that are commonly used across all pages, Thus achieving the right purpose of Inheritance
- Synchronizing the Elements identification and Actions on elements inside Fluent Wait with lambda expression, thus waiting till action is taken not just till finding the element
- Using Bot Pattern as layer of abstraction for multiple interactions with web elements such as type (wait till textBox is located & clear textBox & write on textBox & take screenshot for textBox & print what is written on textBox)
- Implement Util for different Actions with Web Elements / Scrolling / Alerts
- Finding Elements using simple Techniques with ID, CSS Selectors & XPath & Advanced Techniques like XPath Axis and Relative Locators"
- Provide Click using Javascript if the Webdriver Click fails

#### Structure of "test folder"
- Using Base Test Class for defining Annotations to Open and Close Browser, such that all Test Classes inherit from it
- Start each Test from a clean state by Setting and Tearing down Brower for Every Test Case
- Using TestNG Listeners, to perform actions before and after running every suite"
- "Using Assertions as follows:
   - All Assertions are implemented in Page Class to allow the Fluency of Scenarios Steps with Validations like (Navigate.Writesteps..SoftAssertions.HardAssertions)
   - Using Hard assertions after every test & Soft assertions for doing verifications within the test"
- Using Bypass UI Login Method by Login only once for first time and store Session data on Json file, then use them to Run all next Tests with already logged-in user without need to Perform Login before each test

#### Implementing Tests over API layer besides UI Tests to achieve the Automation Test Pyramid
- User Registeration is done over API, then Login is done over GUI once, then Bypass login for all next Tests
- Search for Products is done over API
- Data Validation on Product Details is done over API
- Data Validation on Address /User Details is done over API
- Data Validation on all retrieved Products in Product Page over API instead of Validating them on GUI (Not Implemented yet)
- Implementing API Tests Using RestAssured and Design Patterns, refer to Readme File of RestAssured Project https://github.com/YehiaMetwally95/NotesApiWithRestassured/blob/master/README.md"

#### Reporting Using Allure Report
- Reporting Test Result & Taking Screenshots for Failed Tests and Successful Tests if needed
- Reporting Failed soft assertions and Taking Screenshots 
- Logging All Test Steps and Validations, in form of main steps and expanding every main step to check its child steps
- Defining Epics/Features/Stories"

#### Test Data Management
- Reading Global Variables and Configurations from Properties file, like selecting browser type, execution type, setting capabilities of every browser like headless execution, disable infobars, start maximized, etc
- Test Data Preparation for Live Data like ""UsersData , ProductsData"" by setting Json File for every Test Case which read its data from mysql database using JDBC , And then Before every run, any updates in database will be reflected into Json Files to be used in the tests of next run, Plus Use this test data for Validations
- Test Data Preparation for Static Data like ""Messages, Page Titles, Credit Card Details"" by Filling it Manually on Json Files for every Test Case & User this data for Validations
- Test Data Generation for User Input Data like ""Registeration Inputs"" using Time Stamp for Unique values & Data faker for more Descriptive values and store this data into Json Files to use it for Validations and for next tests in run time
- Test Data Execution by reading test data from Json files whether Json data is represend as Simple Json Object or Nested Json Objects or Array of Json Objects
- After Every User Registeration, The script adds the created User and his details in the Database and then reflect the database updates to Json Files that store the Users Data to be used in next tests"

#### Perform Parallel Execution
- Implementing All Test Methods to be independant in their steps and each Test has its own data in separate Json File, to allow Parallel Execution in Method Level
- Using ThreadLocal for isolating the WebDriver and Enable it to run Parallel tests
- Scaling up Tests to run Parallel Test Cases across Different Browsers Remotely on by help of Docker Containers with Selenium Grid 4 on Hub and Node Mode

#### Using TestNG XML File to Trigger Tests on (Locally/Remotey) & On(Different Browsers) & (Sequencially/ In Parallel)

#### Create CI/CD Pipeline with Github Actions
- Workflow that run all the 14 E2E Tests in Parallel within 90 seconds only, In 2-Tiers of Parallelization 
    - 1st Tier --> Parallel Execution across 6 Jobs, each one runs on different platforms (ubuntu/windows) and browsers (chrome/edge/firefox)
    - 2nd Tier --> Parallel Execution inside each Job with help of Selenium grid and Docker containers 
- Two Workflows, One to run Tests over UI and the other to run the Same Tests over API which is much faster
- Tests runs Locally or Remotely on Selenium Grid and Docker containers
- Run Workflows on Different Triggers: after every Push, Scheduled and Manually
- Generate the Allure Reports after every Workflow run such that 
    - Separate Reports for Tests related to a Single Job
    - Combined Report that Include All Tests that runs on all Jobs 
