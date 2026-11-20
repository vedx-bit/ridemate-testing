package com.ridemate.pages;

import com.ridemate.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // TODO: Open the site, press F12, inspect each element and fix these locators if a test cannot find them.
    private final By emailInput    = By.cssSelector("input[type='email'], input[name='email'], #email");
    private final By passwordInput = By.cssSelector("input[type='password'], #password");
    private final By loginButton   = By.cssSelector("button[type='submit']");
    private final By errorMessage  = By.cssSelector(".error, .alert, .toast, [role='alert'], .text-red-500");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(ConfigReader.get("base.url") + ConfigReader.get("login.path"));
        return this;
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginButton);
    }

    public boolean isLoginFormVisible() {
        return isVisible(emailInput) && isVisible(passwordInput);
    }

    public boolean isErrorVisible() {
        return isVisible(errorMessage);
    }

    public String getPasswordFieldType() {
        return getAttribute(passwordInput, "type");
    }

    /** Short check, used by negative tests. */
    public boolean isLoggedIn() {
        return urlContainsWithin(ConfigReader.get("login.success.url.part"), ConfigReader.getInt("short.wait"));
    }

    /** Long wait, used by the positive login test. */
    public boolean waitUntilLoggedIn() {
        return urlContainsWithin(ConfigReader.get("login.success.url.part"), ConfigReader.getInt("explicit.wait"));
    }
}
