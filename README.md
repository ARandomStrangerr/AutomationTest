# AutomationTest
Automation Test using Selenium.  
In automated testing, it's generally not recommended to explicitly code failing tests unless explicitly state so.
# Installation
- Get [Selenium](https://www.selenium.dev/downloads/)
- Download webdriver for the testing browser.
  - [Chrome](https://chromedriver.chromium.org/downloads)
  - [Firefox](https://github.com/mozilla/geckodriver/releases)
  - [Safari](https://developer.apple.com/documentation/webkit/testing_with_webdriver_in_safari)
```
  WebDriver webDriver = new ChromeDriver();
  System.setProperty(<driver_name>, <web_driver_location>);
```
# Test Case 1
## Scenario
- Sign in
- Create new project
## Pre-condition
- User have an accout with BugBug.io  
- User currently not sign in with BugBug.io
## Test Steps:
1. Sign In:  
- Launch the BugBug.io Sign-in page (https://app.bugbug.io/sign-in/).  
- Enter a valid username or email address in the username/email field.  
- Enter the corresponding password in the password field.  
- Click the "Sign In" button.  
- Verification: Verify that the user is successfully signed in and redirected to the BugBug.io dashboard.  
2. Create New Project:
- Locate the button for creating a new project.  
- Click the "Create New Project" button or navigate to the project creation form.  
- Enter a name and a web page to test, the name of the test must be unique.  
- Click the "Create Project" to confirm project creation.  
## Expected Results:
- User is successfully signed in to BugBug.io after entering valid credentials.  
- A new project is created with the specified name after following the creation process.  
## Pass/Fail Criteria:
- Pass: If the user is able to sign in successfully and create a new project with the desired name.  
- Fail: If the user encounters any errors during sign-in (invalid credentials, server error) or project creation fails (duplicate name, missing information).
# Test Case 2
## Scenario
- Create test management
- Delete test management
- List test management
- Update test management
## Pre-condition
- User signed-in
- User inside a project
## Test Steps:
1. Create a test management
- Navigate to "New Test" button at the top right corner.
- Click the "New Test" button.
- Insert the name for the test.
- Navigate to and click the "Create Test" button.
3. List test management
- The newly created test is listed.  
4. Delete test management
- Nagivate to the deleted item to display the ellipsis.
- Navigate to then click on the ellipsis.
- Navigate to then click on the delete button.
- Confirm the deletion.
5. Update test management
- Navigate to the changed item to display the ellipsis.
- Navigate to then click on the "Rename" funcitonality.
- Fill in the new name of the test
- Confirm the new name by click on "Save test"
## Expected Reslut
- User is able to Create, List, Delete, Update a test cases
## Pass / Fail Criteria:
- Pass: If the user is able to create a test case, then be able to see, modify, and delete the test case
- Fail: If the user is encounter any error during creation (creating dialog not show, cannot confirm creation), list (cannot see newly created test), deletion (the test case is still there after deletion, cannot confirm deletion), or update (cannot change name of test)
# Test Case 3
## Scenario
- Creteation of suite
- Delete test suite
- List test suite
- Update test suite
## Pre-condition
- User is signed-in
- User is inside a project
## Test steps
1. Navigate to test suite page
- Navigate to then click on the "Suite" link on the left of the page
2. Create a suite
- Navigate to "New suite" button at the top right corner.
- Click the "New Suite" button.
- Insert the name for the test.
- Navigate to and click the "Create Suite" button.
3. List suite
- The newly created test is listed.  
4. Delete suite
- Nagivate to the deleted item to display the ellipsis.
- Navigate to then click on the ellipsis.
- Navigate to then click on the delete button.
- Confirm the deletion.
5. Update suite
- Navigate to the changed item to display the ellipsis.
- Navigate to then click on the "Rename" funcitonality.
- Fill in the new name of the test
- Confirm the new name by click on "Save"
## Expected Reslut
- User is able to Create, List, Delete, Update a suite
## Pass / Fail Criteria:
- Pass: If the user is able to create a suite, then be able to see, modify, and delete the test case
- Fail: If the user is encounter any error during creation (creating dialog not show, cannot confirm creation), list (cannot see newly created suite), deletion (the suite is still there after deletion, cannot confirm deletion), or update (cannot change name of suite)
