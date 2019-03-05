package stepdefs.login;

import cucumber.api.java8.En;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import vn.asiantech.base.BaseDefinitions;
import vn.asiantech.page.home.HomePage;
import vn.asiantech.page.login.LoginPage;

import static vn.asiantech.base.Constant.DEFAULT_TIME_OUT;
import static vn.asiantech.base.Constant.LOGIN_PAGE_URL;

public class PortalLoginDefinitions extends BaseDefinitions implements En {
    private LoginPage LoginPage;
    private HomePage homePage;

    public PortalLoginDefinitions() {
        clearCookies();
        Given("^I open login page$", () -> getDriver().get(LOGIN_PAGE_URL));

        Then("^Browser should redirect to \"([^\"]*)\"$", (String path) -> {
            new WebDriverWait(getDriver(), DEFAULT_TIME_OUT).until(
                    webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            try {
                getDriver().findElement(By.id("btn-logout")).click();
            } catch (NotFoundException exception) {
                //no-opt
            }
            waitVisibilityOfElement(getDriver(), By.cssSelector(".middle-box.text-center.loginscreen"));
            String url = getDriver().getCurrentUrl();
            Assert.assertEquals(path, url.substring(url.length() - path.length()));
            LoginPage = initPage(getDriver(), LoginPage.class);
        });

        Given("^I enter my username$",()-> {
            if (LoginPage.hasEmail()) {
                LoginPage.withUsername(getAccount().email);
            }
        });

        And("^I fill in password$", () -> LoginPage.withPassword(getAccount().password));

        Given("^I enter my username with \"([^\"]*)\"$", (String email) -> {
            if (LoginPage.hasEmail()) {
                LoginPage.withUsername(email);
            }
        });

        And("^I fill in password with \"([^\"]*)\"$", (String pwd) -> LoginPage.withPassword(pwd));

        When("^I click on login button$", () -> LoginPage.login());

        Then("^I should see the welcome message$", () -> {
            homePage = initPage(getDriver(), HomePage.class);
            homePage.waitForWelcomeMessage();
            Assert.assertTrue(homePage.hasWelcomeMessage());
            Assert.assertTrue(homePage.welcomeTestIsDisplayed());
        });

        Then("^Login button will be disabled$", () -> Assert.assertFalse(LoginPage.getLoginButton().isEnabled()));

        Then("^Error message should display and show \"([^\"]*)\"$", (String warning) -> {
            LoginPage.waitForErrorMessage();
            Assert.assertTrue(LoginPage.errorMessageIsDisplayed());
            Assert.assertEquals(warning, LoginPage.getErrorMessage());
        });

        Then("^I click on logout button$", () -> homePage.logout());
    }
}
