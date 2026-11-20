package com.ridemate.pages;

import com.ridemate.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    // TODO: verify these locators with F12 on your site.
    private final By nameInput     = By.cssSelector("input[name='name'], input[name='fullName'], #name");
    private final By emailInput    = By.cssSelector("input[type='email'], input[name='email'], #email");
    private final By passwordInput = By.cssSelector("input[type='password'], #password");
    private final By submitButton  = By.cssSelector("button[type='submit']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        driver.get(ConfigReader.get("base.url") + ConfigReader.get("register.path"));
        return this;
    }

    public void register(String name, String email, String password) {
        type(nameInput, name);
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
    }

    public boolean isFormVisible() {
        return isVisible(emailInput) && isVisible(passwordInput);
    }

    /** True if registration was NOT accepted, i.e. we are still on the register page after the short wait. */
    public boolean stillOnRegisterPage() {
        boolean movedAway = urlLeaves(ConfigReader.get("register.path"), ConfigReader.getInt("short.wait"));
        return !movedAway;
    }
}
