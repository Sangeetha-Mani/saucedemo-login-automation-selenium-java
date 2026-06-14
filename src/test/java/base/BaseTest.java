package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.ITestResult;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.PageLoadStrategy;



public class BaseTest {

    public  BaseTest() {
        System.out.println("Constructor baseTest runing");
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        System.out.println("BEFORE CLASS RUNNING");
    }

    // ThreadLocal — critical for parallel execution
    // Think of it like: each thread gets its own isolated driver instance
    // Similar concept to React's isolated component state per instance
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        System.out.println("setup runnig");
        // Read browser from config or system property
        // In CI, you'd pass: -Dbrowser=chrome via Maven
        String browser = System.getProperty("browser", "chrome");

        WebDriver webDriver;

        if (browser.equalsIgnoreCase("firefox")) {
            webDriver = new FirefoxDriver();
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // No UI — for CI/CD
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
             options.addArguments("--remote-allow-origins=*"); 
            
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.addArguments("--no-sandbox");    // Required in Docker
            options.addArguments("--disable-dev-shm-usage"); // Fixes memory issues in containers
           
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            webDriver = new ChromeDriver(options);
        }

        //webDriver.manage( ).window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Store driver in ThreadLocal — this is what makes parallel runs safe
        driver.set(webDriver);
        System.out.println("afterdriver set" + driver.get());
    }

    // Every test class calls this to get its driver
    // Protected = accessible to child classes, not outsiders
    // Like exposing a prop to child components but not to the DOM
    //this is threadlocal driver , it has method like get() to get the chormedriver object
    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) {
        // ITestResult tells you if the test PASSED, FAILED, or SKIPPED
        if (result.getStatus() == ITestResult.FAILURE) {
            // Take screenshot on failure — critical for debugging in CI
            takeScreenshot(result.getName());
        }

        // Always quit the driver — even if test failed
        // Like cleanup in useEffect's return function
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); // Clean up ThreadLocal — prevents memory leak
        }
    }

    private void takeScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
        try {
            FileUtils.copyFile(source, new File(destination));
            System.out.println("Screenshot saved: " + destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
