package at.core;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyListener extends TestListenerAdapter {

    @Override
    public void onFinish(ITestContext context) {
        super.onFinish(context);
        RemoteWebDriver driver = (RemoteWebDriver) context.getAttribute("webDriver");
        Properties prop = new Properties();
        Capabilities capabilities = driver.getCapabilities();
        try {
            OutputStream output = new FileOutputStream("target/cucumber-reports/browser.properties");
            prop.setProperty("BrowserName", capabilities.getBrowserName());
            prop.setProperty("BrowserVersion", capabilities.getVersion());
            prop.setProperty("Platform", capabilities.getPlatform().name());
            String server = context.getCurrentXmlTest().getSuite().getParameter("server");
            if (!server.equals("")) {
                prop.setProperty("Server", context.getCurrentXmlTest().getSuite().getParameter("server"));
            }
            prop.store(output, null);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
    }
}

