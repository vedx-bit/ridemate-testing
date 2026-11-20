package com.ridemate.tests;

import com.ridemate.base.BaseTest;
import com.ridemate.config.ConfigReader;
import com.ridemate.pages.LoginPage;
import com.ridemate.pages.RideSearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RideSearchTests extends BaseTest {

    @Test(groups = {"regression"},
          description = "TC_SEARCH_01 - Logged-in user can search rides; page shows results or a no-rides message")
    public void searchShowsResultsOrEmptyMessage() {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        loginPage.login(ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));
        Assert.assertTrue(loginPage.waitUntilLoggedIn(), "Pre-condition failed: could not log in");

        RideSearchPage searchPage = new RideSearchPage(getDriver());
        searchPage.search(ConfigReader.get("search.from"), ConfigReader.get("search.to"));
        Assert.assertTrue(searchPage.showsResultsOrEmptyState(),
                "Search should show ride cards or a 'no rides' message, not a blank page");
    }
}
