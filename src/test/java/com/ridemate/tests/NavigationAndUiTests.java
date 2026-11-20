package com.ridemate.tests;

import com.ridemate.base.BaseTest;
import com.ridemate.config.ConfigReader;
import com.ridemate.pages.LoginPage;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationAndUiTests extends BaseTest {

    @Test(groups = {"regression"},
          description = "TC_NF_03 - Protected page cannot be opened without logging in")
    public void protectedPageNeedsLogin() {
        getDriver().get(ConfigReader.get("base.url") + ConfigReader.get("protected.path"));
        LoginPage loginPage = new LoginPage(getDriver());
        boolean sentToLogin = loginPage.urlContainsWithin("login", ConfigReader.getInt("short.wait"))
                || loginPage.isLoginFormVisible();
        Assert.assertTrue(sentToLogin,
                "Without login the user should be redirected to login. URL: " + loginPage.getCurrentUrl());
    }

    @Test(groups = {"regression"},
          description = "TC_UI_01 - Login form is usable on a phone-sized screen")
    public void loginFormWorksOnMobileSize() {
        getDriver().manage().window().setSize(new Dimension(375, 812));
        LoginPage loginPage = new LoginPage(getDriver()).open();
        Assert.assertTrue(loginPage.isLoginFormVisible(), "Login form is not visible on a 375x812 screen");
    }
}
