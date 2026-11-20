package com.ridemate.tests;

import com.ridemate.base.BaseTest;
import com.ridemate.config.ConfigReader;
import com.ridemate.pages.LoginPage;
import com.ridemate.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Smoke = "is the app basically alive?". Run these first, before anything else. */
public class SmokeTests extends BaseTest {

    @Test(groups = {"smoke", "regression"}, priority = 1,
          description = "TC_NF_01 - Home page loads (also warms up the sleeping Render server)")
    public void homePageLoads() {
        String url = getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains(ConfigReader.get("base.url").replace("https://", "")),
                "Unexpected URL: " + url);
        Assert.assertFalse(getDriver().getTitle().isEmpty(), "Page title should not be empty");
    }

    @Test(groups = {"smoke", "regression"}, priority = 2,
          description = "TC_LOGIN_09 - Login page opens and shows the form")
    public void loginPageLoads() {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        Assert.assertTrue(loginPage.isLoginFormVisible(), "Login form is not visible");
    }

    @Test(groups = {"smoke", "regression"}, priority = 3,
          description = "TC_REG_07 - Register page opens and shows the form")
    public void registerPageLoads() {
        RegisterPage registerPage = new RegisterPage(getDriver()).open();
        Assert.assertTrue(registerPage.isFormVisible(), "Register form is not visible");
    }
}
