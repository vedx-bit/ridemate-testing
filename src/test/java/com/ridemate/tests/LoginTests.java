package com.ridemate.tests;

import com.ridemate.base.BaseTest;
import com.ridemate.config.ConfigReader;
import com.ridemate.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(groups = {"smoke", "regression"},
          description = "TC_LOGIN_01 - Valid user can log in")
    public void validLogin() {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        loginPage.login(ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));
        Assert.assertTrue(loginPage.waitUntilLoggedIn(),
                "Login did not redirect to the logged-in page. Current URL: " + loginPage.getCurrentUrl());
    }

    /** One test method, many data rows. Add a new row = a new test case. No copy-paste. */
    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                // email,                      password,        scenario
                {"testuser@example.com",       "WrongPass@999", "TC_LOGIN_02 wrong password"},
                {"nobody_xyz@example.com",     "Test@12345",    "TC_LOGIN_03 unregistered email"},
                {"",                           "",              "TC_LOGIN_04 both fields empty"},
                {"testuser@example.com",       "",              "TC_LOGIN_04 empty password"},
                {"not-an-email",               "Test@12345",    "TC_LOGIN_05 invalid email format"},
                {"' OR '1'='1",                "' OR '1'='1",   "TC_LOGIN_06 SQL injection attempt"}
        };
    }

    @Test(dataProvider = "invalidCredentials", groups = {"regression"},
          description = "Negative login scenarios - user must NOT get in")
    public void invalidLoginIsRejected(String email, String password, String scenario) {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        loginPage.login(email, password);
        Assert.assertFalse(loginPage.isLoggedIn(), "User should NOT be logged in. Scenario: " + scenario);
    }

    @Test(groups = {"regression"},
          description = "TC_LOGIN_07 - Password is masked on screen")
    public void passwordFieldIsMasked() {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        Assert.assertEquals(loginPage.getPasswordFieldType(), "password",
                "Password input must be type=password so characters are hidden");
    }
}
