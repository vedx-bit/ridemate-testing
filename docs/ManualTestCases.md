# RideMate - Manual Test Cases

**Application:** https://ridemate-2.onrender.com
**Type of testing:** Functional, Negative, UI/Compatibility, Basic Security
**Priority:** P1 = critical, P2 = important, P3 = nice to have
**Automated?** shows the matching class in `src/test/java/com/ridemate/tests` (or "Manual").

> Before using this sheet: open the site once and edit any step or expected result that does not match
> how your app really behaves (field names, messages, page names).

## 1. Registration

| ID | Scenario | Steps | Expected Result | Priority | Automated? |
|---|---|---|---|---|---|
| TC_REG_01 | Register with valid details | 1. Open Register page 2. Enter valid name, new email, strong password 3. Click Register | Account is created; user is taken to login/home; success message shown | P1 | Manual |
| TC_REG_02 | Register with an email that already exists | 1. Open Register 2. Enter details with an existing email 3. Submit | Error "email already registered"; no new account | P1 | RegisterTests |
| TC_REG_03 | Submit with all fields empty | 1. Open Register 2. Click Register without typing | Form is not submitted; required-field messages shown | P1 | RegisterTests |
| TC_REG_04 | Invalid email format | 1. Enter `abc.com` as email 2. Submit | Validation error; form not submitted | P2 | RegisterTests |
| TC_REG_05 | Very short / weak password | 1. Enter password `123` 2. Submit | Password rule error shown | P2 | Manual |
| TC_REG_06 | Password and confirm password do not match (if the form has this field) | 1. Enter different values 2. Submit | Error "passwords do not match" | P2 | Manual |
| TC_REG_07 | Register page opens | 1. Open `/register` | Form with all fields is visible | P1 | SmokeTests |

## 2. Login / Logout

| ID | Scenario | Steps | Expected Result | Priority | Automated? |
|---|---|---|---|---|---|
| TC_LOGIN_01 | Login with valid credentials | 1. Open Login 2. Enter valid email + password 3. Click Login | User lands on dashboard/home | P1 | LoginTests |
| TC_LOGIN_02 | Wrong password | 1. Valid email + wrong password | Error message; user not logged in | P1 | LoginTests |
| TC_LOGIN_03 | Unregistered email | 1. Enter an email that was never registered | Error message; user not logged in | P1 | LoginTests |
| TC_LOGIN_04 | Empty email and/or password | 1. Leave field(s) blank 2. Click Login | Validation message; user not logged in | P1 | LoginTests |
| TC_LOGIN_05 | Invalid email format | 1. Enter `not-an-email` | Validation message; user not logged in | P2 | LoginTests |
| TC_LOGIN_06 | SQL injection text in fields | 1. Enter `' OR '1'='1` in both fields | Login is rejected; no server error page | P1 | LoginTests |
| TC_LOGIN_07 | Password is hidden | 1. Type in password field | Characters shown as dots/stars | P2 | LoginTests |
| TC_LOGIN_08 | Session after refresh | 1. Login 2. Press F5 | User stays logged in | P2 | Manual |
| TC_LOGIN_09 | Login page opens | 1. Open `/login` | Email, password and Login button visible | P1 | SmokeTests |
| TC_LOGIN_10 | Logout | 1. Login 2. Click Logout 3. Press browser Back | User is logged out; Back does not show private pages | P1 | Manual |

## 3. Search Ride

| ID | Scenario | Steps | Expected Result | Priority | Automated? |
|---|---|---|---|---|---|
| TC_SEARCH_01 | Search with valid From and To | 1. Login 2. Enter From + To 3. Search | Ride list OR a "no rides found" message | P1 | RideSearchTests |
| TC_SEARCH_02 | From and To are the same | 1. Enter same city in both 2. Search | Validation message or empty result, no crash | P3 | Manual |
| TC_SEARCH_03 | Search with empty fields | 1. Leave From/To blank 2. Search | Validation message | P2 | Manual |
| TC_SEARCH_04 | No matching rides | 1. Search a route nobody offers | Friendly "no rides" message, not a blank page | P2 | Manual |

## 4. Offer Ride

| ID | Scenario | Steps | Expected Result | Priority | Automated? |
|---|---|---|---|---|---|
| TC_OFFER_01 | Offer a ride with valid data | 1. Login 2. Fill From, To, date, time, seats, price 3. Submit | Ride is created and visible in search | P1 | Manual |
| TC_OFFER_02 | Date in the past | 1. Choose yesterday's date 2. Submit | Error; ride not created | P2 | Manual |
| TC_OFFER_03 | Zero or negative seats | 1. Enter seats = 0 or -1 2. Submit | Validation error | P2 | Manual |

## 5. Booking

| ID | Scenario | Steps | Expected Result | Priority | Automated? |
|---|---|---|---|---|---|
| TC_BOOK_01 | Book an available ride | 1. Login as passenger 2. Open a ride 3. Click Book | Booking confirmed; seat count reduces | P1 | Manual |
| TC_BOOK_02 | User tries to book their own ride | 1. Login as the ride owner 2. Open own ride | Booking not allowed | P3 | Manual |

## 6. UI and Compatibility

| ID | Scenario | Steps | Expected Result | Priority | Automated? |
|---|---|---|---|---|---|
| TC_UI_01 | Mobile screen size | 1. Resize to 375x812 2. Open Login | Form fully visible, no horizontal scroll | P2 | NavigationAndUiTests |
| TC_UI_02 | Cross-browser | 1. Open the site in Chrome, Firefox, Edge | Same look and behaviour | P2 | Manual (run suite with `-Dbrowser=firefox`) |
| TC_UI_03 | Unknown URL | 1. Open `/this-page-does-not-exist` | Friendly 404 page | P3 | Manual |

## 7. Non-Functional / Security

| ID | Scenario | Steps | Expected Result | Priority | Automated? |
|---|---|---|---|---|---|
| TC_NF_01 | Site loads (Render free tier may sleep) | 1. Open the home page after idle time | Page loads within 60 sec; later loads are fast | P1 | SmokeTests |
| TC_NF_02 | Script typed into a text field | 1. Enter `<script>alert('xss')</script>` as name 2. Submit | Script does not run; no alert pop-up | P1 | RegisterTests |
| TC_NF_03 | Open a private page without login | 1. Logged out, open `/dashboard` directly | Redirected to login | P1 | NavigationAndUiTests |

## Summary

| | Count |
|---|---|
| Total manual test cases | 32 |
| Automated (Selenium + TestNG) | 17 test cases (18 test runs, because TC_LOGIN_04 has two data rows) |
| Still manual | 15 |
