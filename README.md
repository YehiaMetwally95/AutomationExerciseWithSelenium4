### Overveiw
- Using Automation Exercise Website as the Application Under Test
- Very Simple script based on Selenium 4 by Java and Maven Project, without using loops, conditions or any complex logics
- Using TestNG as the Testing Framework
- Using Fluent Page Object Model Design Pattern in writing test script and page actions, thus chaining the scenario steps and validations in one line of code"

### Structure of "main folder"
- Using the HomePage as Parent of all pages that inherit locators and actions of Header & Footer from Homepage also for defining common variables that are commonly used across all pages, Thus achieving the right purpose of Inheritance
- Synchronizing the Elements identification and Actions on elements inside Fluent Wait with lambda expression, thus waiting till action is taken not just till finding the element
- Using Bot Pattern as layer of abstraction for multiple interactions with web elements such as type (wait till textBox is located & clear textBox & write on textBox & take screenshot for textBox & print what is written on textBox)
- Implement Util for different Actions with Web Elements / Scrolling / Alerts
- Finding Elements using simple Techniques with ID, CSS Selectors & XPath & Advanced Techniques like XPath Axis and Relative Locators"

### Structure of "test folder"
- Using Base Test Class for defining Annotations to Open and Close Browser, such that all Test Classes inherit from it
- Start each Test from a clean state by Setting and Tearing down Brower for Every Test Case
- Using TestNG Listeners, to perform actions before and after running every suite"
"Using Assertions as follows:
- All Assertions are implemented in Page Class to allow the Fluency of Scenarios Steps with Validations like (Navigate.Writesteps..SoftAssertions.HardAssertions)
- Using Hard assertions after every test & Soft assertions for doing verifications within the test"
Using Bypass UI Login Method by Login only once for first time and store Session data on Json file, then use them to Run all next Tests with already logged-in user without need to Perform Login before each test

### Implementing Tests over API layer besides UI Tests to achieve the Automation Test Pyramid
- User Registeration is done over API, then Login is done over GUI once, then Bypass login for all next Tests
- Search for Products is done over API
- Data Validation on Product Details is done over API
- Data Validation on Address /User Details is done over API
- Data Validation on all retrieved Products in Product Page over API instead of Validating them on GUI (Not Implemented yet)
- Implementing API Tests Using RestAssured and Design Patterns, refer to (Post of Rest Assured)"

### Reporting Using Allure Report
- Reporting Test Result & Taking Screenshots for Failed Tests and Successful Tests if needed
- Reporting Failed soft assertions and Taking Screenshots 
- Logging All Test Steps and Validations, in form of main steps and expanding every main step to check its child steps
- Defining Epics/Features/Stories"

### Test Data Management
- Reading Global Variables and Configurations from Properties file, like selecting browser type, execution type, setting capabilities of every browser like headless execution, disable infobars, start maximized, etc
- Test Data Preparation for Live Data like ""UsersData , ProductsData"" by setting Json File for every Test Case which read its data from mysql database using JDBC , And then Before every run, any updates in database will be reflected into Json Files to be used in the tests of next run, Plus Use this test data for Validations
- Test Data Preparation for Static Data like ""Messages, Page Titles, Credit Card Details"" by Filling it Manually on Json Files for every Test Case & User this data for Validations
- Test Data Generation for User Input Data like ""Registeration Inputs"" using Time Stamp for Unique values & Data faker for more Descriptive values and store this data into Json Files to use it for Validations and for next tests in run time
- Test Data Execution by reading test data from Json files whether Json data is represend as Simple Json Object or Nested Json Objects or Array of Json Objects
- After Every User Registeration, The script adds the created User and his details in the Database and then reflect the database updates to Json Files that store the Users Data to be used in next tests"

### Perform Parallel Execution
- Implementing All Test Methods to be independant in their steps and each Test has its own data in separate Json File, to allow Parallel Execution in Method Level
- Using ThreadLocal for isolating the WebDriver and Enable it to run Parallel tests
- Scaling up Tests to run Parallel Test Cases across Different Browsers Remotely on by help of Docker Containers with Selenium Grid 4 on Hub and Node Mode
- Using TestNG XML File to Trigger Tests on different browsers Sequencially or In parallel on test level and method level"

### Create CI/CD Pipeline with Github Actions
- Run Tests through workflow Locally, and in Parallel across different browsers and platforms 
- Run Tests through workflow Remotely on Selenium Grid and Docker containers, and in Parallel across different browsers and platforms 
- Run Tests on Event Triggers after every Push , every Pull request, Scheduled and Manually
- Generate the Allure Report after every Workflow run"

### Using Cucumber for Implementing BDD Framework & Dependency Injection Technique
