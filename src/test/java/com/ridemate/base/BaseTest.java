package com.ridemate.base;

import com.ridemate.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * Every test class extends this. A fresh browser is opened before each test
 * and closed after it, so tests never affect each other.
 */
public abstract class BaseTest {

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {
        // -Dbrowser=firefox on the command line wins over testng.xml
        DriverFactory.initDriver(System.getProperty("browser", browser));
        getDriver().get(ConfigReader.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
