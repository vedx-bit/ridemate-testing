package com.ridemate.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RideSearchPage extends BasePage {

    // TODO: these are the most guess-based locators. Inspect your search form with F12 and update them.
    private final By fromInput    = By.cssSelector("input[placeholder*='From' i], input[name='from'], #from");
    private final By toInput      = By.cssSelector("input[placeholder*='To' i], input[name='to'], #to");
    private final By searchButton = By.xpath("//button[contains(translate(., 'SEARCH', 'search'), 'search') or @type='submit']");
    private final By rideCards    = By.cssSelector(".ride-card, [data-testid='ride-card'], .ride-item");
    private final By emptyState   = By.cssSelector(".no-rides, .empty, [data-testid='no-rides']");

    public RideSearchPage(WebDriver driver) {
        super(driver);
    }

    public void search(String from, String to) {
        type(fromInput, from);
        type(toInput, to);
        click(searchButton);
    }

    /** A search must show EITHER ride results OR a friendly "no rides" message. Never a blank page. */
    public boolean showsResultsOrEmptyState() {
        return isVisible(rideCards) || isVisible(emptyState);
    }

    public int getResultsCount() {
        return driver.findElements(rideCards).size();
    }
}
