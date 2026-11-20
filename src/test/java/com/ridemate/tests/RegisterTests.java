package com.ridemate.tests;

import com.ridemate.base.BaseTest;
import com.ridemate.config.ConfigReader;
import com.ridemate.pages.RegisterPage;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTests extends BaseTest {

    @Test(groups = {"regression"},
          description = "TC_REG_02 - Registering with an already used email is rejected")
    public void existingEmailIsRejected() {
        RegisterPage registerPage = new RegisterPage(getDriver()).open();
        registerPage.register("Test User", ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));
        Assert.assertTrue(registerPage.stillOnRegisterPage(),
                "Duplicate email should not be accepted (user must stay on register page)");
    }

    @Test(groups = {"regression"},
          description = "TC_REG_03 - Empty mandatory fields are rejected")
    public void emptyFieldsAreRejected() {
        RegisterPage registerPage = new RegisterPage(getDriver()).open();
        registerPage.register("", "", "");
        Assert.assertTrue(registerPage.stillOnRegisterPage(), "Empty form must not be submitted");
    }

    @Test(groups = {"regression"},
          description = "TC_REG_04 - Invalid email format is rejected")
    public void invalidEmailFormatIsRejected() {
        RegisterPage registerPage = new RegisterPage(getDriver()).open();
        registerPage.register("Test User", "abc.com", "Test@12345");
        Assert.assertTrue(registerPage.stillOnRegisterPage(), "Invalid email must not be accepted");
    }

    @Test(groups = {"regression"},
          description = "TC_NF_02 - Script typed in the name field must not run (basic XSS check)")
    public void scriptInNameFieldDoesNotExecute() {
        RegisterPage registerPage = new RegisterPage(getDriver()).open();
        registerPage.register("<script>alert('xss')</script>", "xss_test@example.com", "Test@12345");
        boolean alertAppeared;
        try {
            getDriver().switchTo().alert();
            alertAppeared = true;
        } catch (NoAlertPresentException e) {
            alertAppeared = false;
        }
        Assert.assertFalse(alertAppeared, "An alert popped up - the app is vulnerable to XSS!");
    }
}
