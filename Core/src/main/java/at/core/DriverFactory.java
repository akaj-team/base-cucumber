package at.core;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.xml.XmlTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.CapabilityType.PROXY;
import static at.core.DriverType.*;

/**
 * DriverFactory
 */
public class DriverFactory {

    // browser name
    private static final String BROWSER_CHROME = "chrome";
    private static final String BROWSER_FIREFOX = "firefox";
    private static final String BROWSER_SAFARI = "safari";
    private static final String BROWSER_IE = "ie";
    private static final String BROWSER_EDGE = "edge";
    private static final String BROWSER_OPERA = "opera";

    public static DriverFactory instance = new DriverFactory();
    private static RemoteWebDriver driver;
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    private ThreadLocal<RemoteWebDriver> driverFactoryThread = new ThreadLocal<>();
    private ITestContext context;

    private static Map<String, String> defaultParam() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("browserName", BROWSER_CHROME);
        parameters.put("headless", "false");
        parameters.put("server", "");
        return parameters;
    }

    public static final void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String getOfficialBrowserName(final String browserName) {
        String officialName;
        switch (browserName) {
            case BROWSER_IE:
                officialName = "internet explorer";
                break;
            case BROWSER_EDGE:
                officialName = "MicrosoftEdge";
                break;
            case BROWSER_OPERA:
                officialName = "operablink";
                break;
            case BROWSER_CHROME:
            case BROWSER_FIREFOX:
            case BROWSER_SAFARI:
            default:
                officialName = browserName;
        }
        return officialName;
    }

    private DriverType getDriverType(final String browserName) {
        DriverType driverType;
        switch (browserName) {
            case BROWSER_CHROME:
                driverType = CHROME;
                break;
            case BROWSER_FIREFOX:
                driverType = FIREFOX;
                break;
            case BROWSER_SAFARI:
                driverType = SAFARI;
                break;
            case BROWSER_IE:
                driverType = IE;
                break;
            case BROWSER_EDGE:
                driverType = EDGE;
                break;
            case BROWSER_OPERA:
                driverType = OPERA;
                break;
            default:
                driverType = CHROME;
        }
        return driverType;
    }

    private void instantiateWebDriver(final DriverType driverType, final String server, final boolean isHeadLess) throws MalformedURLException {
        System.out.println(" ");
        System.out.println("Local Operating System: " + operatingSystem);
        System.out.println("Local Architecture: " + systemArchitecture);
        System.out.println("Selected Browser: " + driverType);
        System.out.println("Selected server: " + server);
        System.out.println(" ");

        RemoteWebDriver driver;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (proxyEnabled) {
            Proxy proxy = new Proxy();
            proxy.setProxyType(MANUAL);
            proxy.setHttpProxy(proxyDetails);
            proxy.setSslProxy(proxyDetails);
            desiredCapabilities.setCapability(PROXY, proxy);
        }

        if (server != null && !server.isEmpty()) {
            URL seleniumGridURL = new URL(server);
            desiredCapabilities.setBrowserName(getOfficialBrowserName(driverType.toString()));
            driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            driver = driverType.getWebDriverObject(desiredCapabilities, isHeadLess);
            this.driver = driver;
        }

        driverFactoryThread.set(driver);
        if (context != null) {
            context.setAttribute("webDriver", driver);
        }
    }

    public final synchronized RemoteWebDriver getDriver() {
        if (driverFactoryThread.get() == null) {
            XmlTest xmlTest = new XmlTest();
            xmlTest.setParameters(defaultParam());
            startDriver(xmlTest);
        }
        return driverFactoryThread.get();
    }

    public synchronized void startDriver(final XmlTest xmlTest) {
        //get param suite
        String browserName = xmlTest.getParameter("browserName");
        String server = xmlTest.getParameter("server");
        String headLess = xmlTest.getParameter("headless");

        DriverType driverType = getDriverType(browserName);
        try {
            instantiateWebDriver(driverType, server, Boolean.parseBoolean(headLess));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void startDriver(final ITestContext context) {
        this.context = context;
        startDriver(context.getCurrentXmlTest());
    }

    public final void quitDriver() {
        if (driverFactoryThread.get() != null) {
            driverFactoryThread.get().quit();
        }
    }
}
