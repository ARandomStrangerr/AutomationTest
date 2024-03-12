import automation.AutomationTest;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) throws Exception{
        test3();
    }

    /**
     * Test #1: Sign in, create new project
     * step 1: open the webpage
     * we will be greeted with log-in form
     * step 2: fill in the log-in information
     * fill in the username and password to the correct field
     * step 3: submit the log-in form
     * we submit the log-in form to log in, check if the credential is correct
     * step 4: get the create project form
     * if the "new project" form pop-up right after logging in, there is no project yet
     * if the "new project" form does not pup-up, manually click on the "new project" button to show the form
     * step 5: fill in the project name and project URL
     * step 6: submit the form to create a new project
     *
     * @throws InterruptedException
     */
    public static void test1() throws InterruptedException {
        AutomationTest chromeAutomation = new AutomationTest(new ChromeDriver(), "webdriver.chrome.driver", "./lib/chromedriver_mac_arm64", 1000);
        chromeAutomation.openWebsite("https://app.bugbug.io/sign-in/") // open the sign-in page
                .login("ttd9@sfu.ca", "hungcom23")
                .createNewProject("New Project 2", "https://www.google.com")
                .cleanup(); // close the web browser
    }

    /**
     * Test #2: Tests management page (including creating, deleting, list, update)
     * step 1: open the webpage
     * we will be greeted with log-in form
     * step 2: fill in the log-in information
     * fill in the username and password to the correct field
     * step 3: submit the log-in form
     * we submit the log-in form to log in, check if the credential is correct
     * step 4: arrive at the project page, select the wanted project to test
     * if the project exist, access it, if not, the test stop here
     * step 5: CREATE test cases
     * if the name of a test cases already exists, an error is thrown and the test stop
     * step 6: LIST out all test cases
     * step 7: DELETE one test bases on name
     * step 8: UPDATE name of a test
     * step 9: DELETE ALL test case
     * step 10: close the windows
     * @throws Exception
     */
    public static void test2() throws Exception {
        AutomationTest chromeAutomation = new AutomationTest(new ChromeDriver(), "webdriver.chrome.driver", "./lib/chromedriver_mac_arm64", 2000);
        chromeAutomation.openWebsite("https://app.bugbug.io/sign-in/") // open the sign-in page
                .login("ttd9@sfu.ca", "hungcom23") // attempt to log-in into the web app
                .selectProject("New Project 2") // select a project to test
                .createTest("Test No.1") // create test name test "Test No.1"
                .createTest("Test No.2") // create test name test "Test No.2"
                .createTest("Test No.3") // create test name test "Test No.3"
                .listOutAllTest() // list out all the existed tests
                .deleteOneTest("Test No.1") // test delete one function
                .renameTestCase("Test No.2", "Test No.4") // update name of a test
                .deleteAllTests() // delete all the test
                .cleanup(); // close the browser
    }

    /**
     * Test #3: Suites management(including creating, deleting, list, update)
     * Step 1: open the webpage
     * we will be greeted with log-in form
     * step 2: fill in the log-in information
     * fill in the username and password to the correct field
     * step 3: submit the log-in form
     * we submit the log-in form to log in, check if the credential is correct
     * step 4: navigate to Suite page
     * if there is test case, we can just click on the suite button
     * if there is no test case, we have to close the form first before can click on suite button
     * step 5: CREATE a new suite
     * step 6: UPDATE a suite
     * step 7: DELETE a suite
     * step 8: close the browser
     * @throws Exception
     */
    public static void test3() throws Exception {
        AutomationTest chromeAutomation = new AutomationTest(new ChromeDriver(), "webdriver.chrome.driver", "./lib/chromedriver_mac_arm64", 2000);
        chromeAutomation.openWebsite("https://app.bugbug.io/sign-in/") // open the sign-in page
                .login("ttd9@sfu.ca", "hungcom23") // attempt to log-in into the web app
                .selectProject("New Project 2") // select a project to test
                .openSuitePage() // go to the suite page
                .createSuite("New Suite 1") // crate a suite name New Suite 1
                .listOutAllTest() // list out all suites
                .updateSuiteName("New Suite 1", "New Suite 123") // change the name of the suite to New Suite 123
                .deleteOneSuite("New Suite 123") // delete the suite name New Suite 123
                .cleanup(); // close the browser
    }
}