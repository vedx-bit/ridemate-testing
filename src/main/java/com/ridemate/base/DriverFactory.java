package com.ridemate.base;

import com.ridemate.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Creates and stores the WebDriver.
 * ThreadLocal keeps one driver per thread, so tests can run in parallel safely.
 * Selenium 4 downloads the right browser driver automatically (Selenium Manager).
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() { }

    public static void initDriver(String browser) {
        boolean headless = ConfigReader.getBoolean("headless");
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefox = new FirefoxOptions();
                if (headless) {
                    firefox.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefox);
                break;
            case "edge":
                EdgeOptions edge = new EdgeOptions();
                if (headless) {
                    edge.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edge);
                break;
            case "chrome":
            default:
                ChromeOptions chrome = new ChromeOptions();
                chrome.addArguments("--disable-notifications", "--window-size=1920,1080");
                if (headless) {
                    chrome.addArguments("--headless=new");
                }
                driver = new ChromeDriver(chrome);
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
