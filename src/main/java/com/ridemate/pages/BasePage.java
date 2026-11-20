package com.ridemate.pages;

import com.ridemate.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Parent of every page class. Holds the common actions so each page stays small.
 * We use ONLY explicit waits (no implicit wait, no Thread.sleep in tests).
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final int shortWait = ConfigReader.getInt("short.wait");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText().trim();
    }

    protected String getAttribute(By locator, String attribute) {
        return waitVisible(locator).getAttribute(attribute);
    }

    /** Quick check (short wait). Use for "should NOT be there" style assertions. */
    protected boolean isVisible(By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(shortWait))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean urlContainsWithin(String part, int seconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.urlContains(part));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Waits up to N seconds for the URL to stop containing the given text. True = we moved away. */
    public boolean urlLeaves(String part, int seconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.not(ExpectedConditions.urlContains(part)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
