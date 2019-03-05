package vn.asiantech.page.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import vn.asiantech.base.BasePage;

import static vn.asiantech.base.Constant.LOGIN_PAGE_URL;

public class LoginPage extends BasePage<LoginPage> {
    @FindBy(css = "input[formcontrolname=email]")
    private WebElement usernameInput;
    @FindBy(css = "input[formcontrolname=password]")
    private WebElement PasswordInput;
    @FindBy(css = "button.btn-primary")
    private WebElement LoginButton;
    @FindBy(className = "text-danger")
    private WebElement errorText;

    private WebDriver driver;

    public LoginPage(final WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    @Override
    public final LoginPage navigateTo(final WebDriver webDriver) {
        webDriver.get(LOGIN_PAGE_URL);
        return this;
    }

    public LoginPage login() {
        LoginButton.click();
        return this;
    }

    public final LoginPage withUsername(final String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public final LoginPage withPassword(final String password) {
        PasswordInput.sendKeys(password);
        return this;
    }

    public final void waitForLoginButton() {
        waitForElement(driver, LoginButton);
    }

    public final boolean hasEmail() {
        return isElementPresented(usernameInput);
    }

    public final boolean errorMessageIsDisplayed() {
        return errorText.isDisplayed();
    }

    public final String getErrorMessage() {
        return errorText.getText();
    }

    public final WebElement getLoginButton() {
        return LoginButton;
    }

    public final void waitForErrorMessage() {
        waitForElement(driver, errorText);
    }
}
