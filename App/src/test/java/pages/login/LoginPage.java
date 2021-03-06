package pages.login;

import at.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Constant;

public class LoginPage extends BasePage<LoginPage> {
    @FindBy(css = "input[formcontrolname=email]")
    private WebElement usernameInput;
    @FindBy(css = "input[formcontrolname=password]")
    private WebElement passwordInput;
    @FindBy(css = "button.btn-primary")
    private WebElement loginButton;
    @FindBy(className = "text-danger")
    private WebElement errorText;

    public LoginPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    public final LoginPage open() {
        getDriver().get(Constant.LOGIN_PAGE_URL);
        return this;
    }

    public LoginPage login() {
        loginButton.click();
        return this;
    }

    public final LoginPage withUsername(final String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public final LoginPage withPassword(final String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public final void waitForLoginButton() {
        waitForElement(getDriver(), loginButton);
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
        return loginButton;
    }

    public final void waitForErrorMessage() {
        waitForElement(getDriver(), errorText);
    }
}
