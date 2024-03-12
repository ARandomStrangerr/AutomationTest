package automation;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class AutomationTest {
    private final WebDriver driver;
    private final int waitTime;

    public AutomationTest(WebDriver driver, String driverName, String webDriverLocation, int waitTime) {
        System.setProperty(driverName, webDriverLocation);
        this.driver = driver;
        this.waitTime = waitTime;
    }

    /**
     * open a specific website
     *
     * @param url URL of the website
     * @return the instance of this class being use
     */
    public AutomationTest openWebsite(String url) {
        driver.get(url);
        return this;
    }

    /**
     * sign-in action
     *
     * @param username login email
     * @param password login password
     * @return the instance of this class being use
     * @throws InterruptedException
     * @throws InvalidArgumentException when the credential is incorrect
     */
    public AutomationTest login(String username, String password) throws InterruptedException, InvalidArgumentException {
        String originalUrl = driver.getCurrentUrl();
        List<WebElement> inputElements = driver.findElements(By.cssSelector("[data-testid='Input']")); // obtain the username and password fields
        WebElement loginForm = driver.findElement(By.cssSelector("[data-testid='LoginForm']")); // obtain the login form
        inputElements.get(0).clear();
        inputElements.get(0).sendKeys(username); // fill in the username
        inputElements.get(1).clear();
        inputElements.get(1).sendKeys(password); // fill in the password
        loginForm.submit(); // submit the form to login
        Thread.sleep(waitTime); // wait for the new web page to load (if it proceeds)
        String finalUrl = driver.getCurrentUrl();
        if (originalUrl.equals(finalUrl)) // if we are still at the same page, then the credential is incorrect
            throw new InvalidArgumentException("The given username and password is incorrect");
        return this;
    }

    /**
     * @param projectName name of the new project
     * @param projectURL  the URL webpage of the project
     * @return the instance of this class being use
     * @brief create a new project at "Your Project" page
     */
    public AutomationTest createNewProject(String projectName, String projectURL) throws InterruptedException {
        WebElement projectModal;
        List<WebElement> inputElements;
        try { // case when there is no project yet, a new pop-up form is shown
            projectModal = driver.findElement(By.cssSelector("[data-testid='ProjectModal']")); // get the "new project" modal
        } catch (Exception e) { // case when there is a project already, new project button has to be clicked manually
            driver.findElement(By.cssSelector("[data-testid='ProjectList.NewProjectButton']")).click(); // click on the "New project" button
            Thread.sleep(1000); // wait for the "Project Modal form" to show up, it is a little slow sometimes
            projectModal = driver.findElement(By.cssSelector("[data-testid='ProjectModal']")); // get the "new project" modal
        }
        inputElements = driver.findElements(By.cssSelector("[data-testid='Input']")); // obtain the "Project Name" and "Homepage URL" fields
        inputElements.get(0).sendKeys(projectName); // fill in the "project name"
        inputElements.get(1).sendKeys(projectURL); // fill in the "homepage URL" fields
        projectModal.submit(); // submit the form to create a new project
        return this;
    }

    /**
     * select the first project which match with the given name at "Your Project" page
     *
     * @param projectName name of the project to select
     * @return the instance of this class being use
     * @throws NotFoundException when the given projectName is not found
     */
    public AutomationTest selectProject(String projectName) throws NoSuchElementException, InterruptedException {
        List<WebElement> elements = driver.findElements(By.cssSelector("[data-testid='ProjectItem']")); // get the list of all projects
        if (elements.size() == 0)
            throw new NoSuchElementException("There is no " + projectName); // if the list is empty then certainly the <projectName> does not exist
        for (WebElement element : elements) { // linear searches through the project listed on the screen
            if (element.findElement(By.cssSelector("p")).getText().equals(projectName)) { // if the name of the project match with the given name
                element.click(); // click on the matched element
                Thread.sleep(waitTime); // wait for the web page to load
                return this;
            }
        }
        throw new NoSuchElementException("There is no " + projectName); // when the given project name cannot be found
    }

    /**
     * create a test with specific name then back to "Tests" page
     *
     * @param testName the name of the test
     * @return the instance of this class being use
     */
    public AutomationTest createTest(String testName) throws InterruptedException {
        WebElement testEditModalForm;
        try { // when the project is brand new, there is no test yet, Test Edit Modal Form will be pop-up
            testEditModalForm = driver.findElement(By.cssSelector("[data-testid='EditTestModal']")); // get the new test form
        } catch (
                NoSuchElementException e) { // when there is already test, click the "New Test" button to get the Test Edit Modal Form
            driver.findElement(By.cssSelector("[data-testid='TestsActions.CreateNewTest']")).click(); // click the "New Test" button
            Thread.sleep(waitTime); // wait for the form to be loaded up
            testEditModalForm = driver.findElement(By.cssSelector("[data-testid='EditTestModal']")); // get the new test form
        }
        WebElement testNameField = testEditModalForm.findElement(By.cssSelector("[data-testid='Input']")); // get the Test Name field
        testNameField.sendKeys(testName); // fill in the field with the given testName
        testEditModalForm.submit(); // submit the form to create a new test
        Thread.sleep(waitTime); // wait for the form submission to complete
        try { // try to click the back button, if not, the new test form still persist, the test name is already exists
            driver.findElement(By.cssSelector("[data-testid='IconButton']")).click(); // back to the "Tests" page
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(testName + " already exists");
        }
        Thread.sleep(waitTime); // wait for the page to load up
        return this;
    }

    /**
     * test delete all test cases functionality
     *
     * @return the instance of this class being use
     * @throws InterruptedException
     */
    public AutomationTest deleteAllTests() throws InterruptedException {
        driver.findElement(By.cssSelector("[data-testid='SelectionCell.Input']")).click(); // click the select all button
        Thread.sleep(waitTime);
        driver.findElements(By.cssSelector("[data-testid='Dropdown.Button']")).get(1).click(); // click on the dropdown button on top
        Thread.sleep(waitTime);
        driver.findElement(By.cssSelector("[data-testid='Dropdown.Items']")).findElements(By.cssSelector("button")).get(3).click(); // delete all
        Thread.sleep(waitTime);
        driver.findElement(By.cssSelector(".sc-tagGq.fxwkFr.sc-fXSgeo.cLzWpS")).click(); // confirm deletion
        return this;
    }

    /**
     * delete one test case based on its name
     *
     * @param name name of the test case
     * @return the instance of this class being use
     * @throws InterruptedException
     */
    public AutomationTest deleteOneTest(String name) throws InterruptedException {
        for (WebElement element : driver.findElements(By.cssSelector(".sc-fWKdJz.hLNAkQ"))) { // linear search through test case
            if (element.findElement(By.cssSelector("[data-testid='TextCell']")).getText().equals(name)) { // if the test case is found
                Actions action = new Actions(driver);
                action.moveToElement(element).perform(); // hover on top of the element to make the dropdown button appear
                element.findElement(By.cssSelector("[data-testid='Dropdown']")).click(); // click on the dropdown button
                Thread.sleep(waitTime);
                driver.findElement(By.cssSelector("[data-testid='TestRowActions.DeleteButton']")).click(); // click on the delete button
                Thread.sleep(waitTime);
                driver.findElements(By.cssSelector("[data-testid='Button']")).get(1).click(); // confirm deletion
                Thread.sleep(waitTime);
                return this;
            }
        }
        return this; // either the test case is found and delete or not found at the first place, it does not exist at the end.
    }

    /**
     * print out all test case
     *
     * @return the instance of this class being use
     */
    public AutomationTest listOutAllTest() {
        for (WebElement element : driver.findElements(By.cssSelector(".sc-fWKdJz.hLNAkQ")))
            System.out.println(element.findElement(By.cssSelector("[data-testid='TextCell']")).getText());
        return this;
    }

    /**
     * rename a test
     *
     * @param originalName original name of the test case
     * @param newName new name of the test case
     * @return the instance of this class being use
     * @throws InterruptedException
     */
    public AutomationTest renameTestCase(String originalName, String newName) throws InterruptedException {
        for (WebElement element : driver.findElements(By.cssSelector(".sc-fWKdJz.hLNAkQ"))) { // linear search original name
            if (element.findElement(By.cssSelector("[data-testid='TextCell']")).getText().equals(originalName)) { // if the original name is found
                Actions action = new Actions(driver);
                action.moveToElement(element).perform(); // hover the element to make the dropdown button appear
                element.findElement(By.cssSelector("[data-testid='Dropdown']")).click(); // click on the dropdown button
                Thread.sleep(waitTime);
                driver.findElement(By.cssSelector("[data-testid='TestRowActions.EditButton']")).click(); // click on the edit name button
                Thread.sleep(waitTime);
                WebElement testEditModalForm = driver.findElement(By.cssSelector("[data-testid='EditTestModal']")); // get the new test form
                WebElement testNameField = testEditModalForm.findElement(By.cssSelector("[data-testid='Input']")); // get the Test Name field
                testNameField.clear(); // delete the current field
                testNameField.sendKeys(newName); // set the new name
                testEditModalForm.submit();
                Thread.sleep(waitTime);
                return this;
            }
        }
        return this;
    }

    /**
     * navigate to suite page
     *
     * @return the instance of this class being use
     * @throws InterruptedException
     */
    public AutomationTest openSuitePage() throws InterruptedException{
        try { // if there is no test, new test form pup-up, close it
            WebElement testEditModalForm = driver.findElement(By.cssSelector("[data-testid='EditTestModal']")); // get the test form
            testEditModalForm.findElement(By.cssSelector("[data-testid='EditTestModal.CancelButton']")).click(); // close the create new test form
        } catch (Exception ignored) {}
        driver.findElements(By.cssSelector("[data-testid='Link']")).get(3).click(); // open the suites page
        Thread.sleep(waitTime);
        return this;
    }

    /**
     * crate a new suite
     *
     * @param suiteName name of the new suite
     * @return the instance of this class being use
     * @throws InterruptedException
     */
    public AutomationTest createSuite(String suiteName) throws InterruptedException {
        driver.findElement(By.cssSelector("[data-testid='SuitesActions.CreateNewSuite']")).click(); // click on the create new item button
        Thread.sleep(waitTime);
        WebElement newSuitForm = driver.findElement(By.cssSelector("[data-testid='SuiteModal']")); // get the new suite form
        newSuitForm.findElements(By.cssSelector("[data-testid='Input']")).get(1).sendKeys(suiteName); // write the name to the name input
        newSuitForm.submit(); // submit the form
        Thread.sleep(waitTime);
        return this;
    }

    /**
     * update a suite name when it is at suite page
     * @param originalName the name to select the suite to change name
     * @param newName the new name of the selected suite
     * @return the instance of this class being use
     * @throws InterruptedException
     * @throws NoSuchElementException when there is no suite associated with the given originalName
     */
    public AutomationTest updateSuiteName(String originalName, String newName) throws NoSuchElementException, InterruptedException{
        for (WebElement element : driver.findElements(By.cssSelector(".sc-fWKdJz.hLNAkQ"))) { // linear search original name
            if (element.findElement(By.cssSelector("[data-testid='TextCell']")).getText().equals(originalName)) { // if a suite is found matching the name
                element.click(); // click on the suite
                Thread.sleep(waitTime);
                WebElement form = driver.findElement(By.cssSelector("[data-testid='SuiteModal']")); // get the form to submit
                WebElement nameField = form.findElements(By.cssSelector("[data-testid='Input']")).get(1); // get the name input field
                nameField.clear(); // clear the content of the input
                nameField.sendKeys(newName); // write the new name of the input
                form.submit(); // submit the form
                Thread.sleep(waitTime);
                return this;
            }
        }
        throw new NoSuchElementException("No suite under the name " + originalName); // when the given name is not associated with any Suite
    }

    /**
     * delete a suite based on the given name
     * @param name select the suite to delete
     * @return the instance of this class being use
     * @throws InterruptedException
     */
    public AutomationTest deleteOneSuite(String name) throws InterruptedException {
        for (WebElement element : driver.findElements(By.cssSelector(".sc-fWKdJz.hLNAkQ"))) { // linear search through suites
            if (element.findElement(By.cssSelector("[data-testid='TextCell']")).getText().equals(name)) { // if the suite is found
                Actions action = new Actions(driver);
                action.moveToElement(element).perform(); // hover on top of the element to make the dropdown button appear
                element.findElement(By.cssSelector("[data-testid='Dropdown']")).click(); // click on the dropdown button
                Thread.sleep(waitTime);
                driver.findElement(By.cssSelector("[data-testid='SuiteRowActions.DeleteButton']")).click(); // click on the delete button
                Thread.sleep(waitTime);
                driver.findElement(By.cssSelector("[data-testid='ConfirmModal.ConfirmButton']")).click(); // confirm deletion
                Thread.sleep(waitTime);
                return this;
            }
        }
        return this; // either the suite case is found and delete or not found at the first place, it does not exist at the end.
    }

    /**
     * close the web-browser
     */
    public void cleanup() throws InterruptedException {
        Thread.sleep(5000); // wait to check the result
        driver.quit();
    }
}
