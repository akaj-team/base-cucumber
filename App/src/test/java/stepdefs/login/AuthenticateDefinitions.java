package stepdefs.login;

import at.base.BaseDefinitions;
import cucumber.api.java8.En;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.testng.Assert;
import pages.home.HomePage;
import pages.login.LoginPage;
import utils.Constant;

public class AuthenticateDefinitions extends BaseDefinitions implements En {

    public AuthenticateDefinitions() {

        LoginPage loginPage = initPage(getDriver(), LoginPage.class);
        HomePage homePage = initPage(getDriver(), HomePage.class);

        Given("^I am logged in as a team manager$", () -> {
            if (!isButtonLogoutDisplayed()) {
                getDriver().get(Constant.LOGIN_PAGE_URL);
                loginPage.waitForLoginButton();
                loginPage.withUsername("stg.tien.hoang@asiantech.vn").withPassword("Abc123@@").login();

                homePage.waitForWelcomeMessage();
                Assert.assertTrue(homePage.welcomeTestIsDisplayed());
            }
        });

        Given("^I am an unauthenticated user$", this::logoutCurrentSession);
    }

    private void logoutCurrentSession() {
        try {
            getDriver().findElement(By.id("btn-logout")).click();
        } catch (NotFoundException exception) {
            //no-opt
        }
        waitVisibilityOfElement(getDriver(), By.cssSelector(".middle-box.text-center.loginscreen"));
    }

    private boolean isButtonLogoutDisplayed() {
        try {
            getDriver().findElement(By.id("btn-logout")).isDisplayed();
            return true;
        } catch (NotFoundException exception) {
            return false;
        }
    }
}
