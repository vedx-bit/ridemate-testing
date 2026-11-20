# RideMate - Test Automation Framework

Selenium WebDriver + TestNG + Maven framework (Page Object Model) for the RideMate ride-sharing web app.

**App under test:** https://ridemate-2.onrender.com

## Tech stack

| Tool | Purpose |
|---|---|
| Java 17 | Language |
| Selenium WebDriver 4 | Browser automation |
| TestNG | Test runner, groups, data providers, listeners |
| Maven | Build and dependency management |
| Page Object Model | Keeps locators and actions out of the tests |

## Project structure

```
ridemate-automation-framework/
|-- pom.xml                      # dependencies + surefire plugin
|-- testng.xml                   # full suite (regression group)
|-- testng-smoke.xml             # quick smoke suite
|-- docs/ManualTestCases.md      # 32 manual test cases
`-- src
    |-- main/java/com/ridemate
    |   |-- base/DriverFactory.java        # creates Chrome/Firefox/Edge (ThreadLocal)
    |   |-- config/ConfigReader.java       # reads config.properties
    |   |-- pages/                         # BasePage, LoginPage, RegisterPage, RideSearchPage
    |   `-- utils/ScreenshotUtil.java      # screenshot on failure
    `-- test
        |-- java/com/ridemate
        |   |-- base/BaseTest.java         # open browser before / close after each test
        |   |-- listeners/TestListener.java
        |   `-- tests/                     # SmokeTests, LoginTests, RegisterTests, ...
        `-- resources/config.properties    # URL, timeouts, test data
```

## Framework design

- **Page Object Model** - each page has its own class with locators and actions; tests only describe *what* to check.
- **BasePage** - common click/type/wait methods shared by all pages.
- **Explicit waits only** - no `Thread.sleep`, no implicit wait.
- **DataProvider** - one test method runs many negative-login rows.
- **Groups** - `smoke` (quick health check) and `regression` (everything).
- **Listener** - prints PASS/FAIL and saves a screenshot to `screenshots/` when a test fails.
- **Config file** - URL, timeouts and credentials are not hard-coded in tests.
- **ThreadLocal driver** - safe to run tests in parallel later.

## Prerequisites

- JDK 17 or newer
- Maven 3.8+
- Google Chrome (Firefox / Edge optional)

## Setup

1. Create a test account on the site.
2. Put its details in `src/test/resources/config.properties` (or pass them on the command line - see below).
3. Open the site, press **F12**, and check that the locators in the `pages/` classes match the real page.

## How to run

```bash
# full suite
mvn clean test

# only the quick smoke suite
mvn clean test -DsuiteXmlFile=testng-smoke.xml

# another browser
mvn clean test -Dbrowser=firefox

# headless (no browser window)
mvn clean test -Dheadless=true

# pass your own test account (nothing secret is committed)
mvn clean test -Dvalid.email=you@example.com -Dvalid.password=YourPass@123
```

Results: `target/surefire-reports/index.html` (open in a browser). Failure screenshots: `screenshots/`.

## Test coverage

32 manual test cases are written in [`docs/ManualTestCases.md`](docs/ManualTestCases.md).
17 of them are automated (18 test runs, because the negative-login case runs with several data rows):

| Area | Automated checks |
|---|---|
| Smoke | Home page loads, Login page opens, Register page opens |
| Login | Valid login, wrong password, unknown email, empty fields, bad email format, SQL-injection text, masked password |
| Registration | Duplicate email, empty form, invalid email, script in name field (basic XSS check) |
| Search | Logged-in user searches a route and sees rides or a "no rides" message |
| UI / Security | Mobile screen size, private page needs login |

## Notes

- The app is hosted on Render's free tier, which sleeps when idle. The **first** page load can take 30-60 seconds, so `page.load.timeout` is set to 90 seconds.
- Locators use generic CSS selectors (`input[type='email']`, `button[type='submit']`, ...). If the app uses different attributes, update them in the page classes; no test code needs to change.
- Do not commit real passwords.
